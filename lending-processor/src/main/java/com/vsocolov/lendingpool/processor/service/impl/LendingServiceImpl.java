package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.data.LoanInfo;
import com.vsocolov.lendingpool.processor.service.BundleService;
import com.vsocolov.lendingpool.processor.service.FinancialService;
import com.vsocolov.lendingpool.processor.service.LendingServiceTemplate;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LendingServiceImpl extends LendingServiceTemplate {

    @Autowired
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Autowired
    private BundleService bundleService;

    @Autowired
    private FinancialService financialService;

    private Integer lendingPeriodMonths;

    @Required
    public void setLendingPeriodMonths(final Integer lendingPeriodMonths) {
        this.lendingPeriodMonths = lendingPeriodMonths;
    }

    @Override
    public List<Lender> fetchLendingPool(final List<LenderRecord> lenderRecords) {
        return lenderRecords.stream()
                .map(record -> lenderRecordToLenderConverter.convert(record))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Double, Double> aggregateLoanBundleDetails(final List<Lender> lendingPool, final double loanAmount) {
        final List<LendingBundle> lendingBundles = bundleService.findLendingBundles(lendingPool, loanAmount);
        final Map<Double, Double> aggregationResult = new HashMap<>();

        lendingBundles.forEach(bundle ->
                aggregationResult.merge(bundle.getLender().getRate(), bundle.getAmount(), (a, b) -> a + b));

        return aggregationResult;
    }

    @Override
    public Optional<LoanInfo> calculateLoan(final Map<Double, Double> loanBundleDetails, final double loanAmount) {
        if (MapUtils.isNotEmpty(loanBundleDetails) && loanAmount > 0) {
            double commonRate = financialService.computeCommonRate(loanBundleDetails);

            double monthlyRepayment = financialService.pmt(commonRate, lendingPeriodMonths, loanAmount);

            double totalRepayment = monthlyRepayment * lendingPeriodMonths;

            return Optional.of(new LoanInfo(loanAmount, commonRate, monthlyRepayment, totalRepayment));
        }

        return Optional.empty();
    }
}
