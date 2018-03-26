package com.vsocolov.lendingpool.processor.service;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LoanInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class LendingServiceTemplate {

    protected abstract List<Lender> fetchLendingPool(List<LenderRecord> lenderRecords);

    protected abstract Map<Double, Double> aggregateLoanBundleDetails(List<Lender> lendingPool, double loanAmount);

    protected abstract Optional<LoanInfo> calculateLoan(Map<Double, Double> loanBundleDetails, double loanAmount);

    public Optional<LoanInfo> fetchLoanInfo(final double loanAmount, final List<LenderRecord> lenderRecords) {

        final List<Lender> lenders = fetchLendingPool(lenderRecords);

        final Map<Double, Double> loanBundleDetails = aggregateLoanBundleDetails(lenders, loanAmount);

        return calculateLoan(loanBundleDetails, loanAmount);
    }
}
