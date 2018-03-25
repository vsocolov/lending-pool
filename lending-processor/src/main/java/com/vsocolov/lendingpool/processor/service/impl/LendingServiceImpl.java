package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.service.BundleService;
import com.vsocolov.lendingpool.processor.service.LendingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LendingServiceImpl implements LendingService {

    @Autowired
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Autowired
    private BundleService bundleService;

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
}
