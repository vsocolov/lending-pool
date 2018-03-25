package com.vsocolov.lendingpool.processor.service;

import java.util.Map;

public interface FinancialService {
    double pmt(double yearlyInterestRate, int totalNumberOfMonths, double loanAmount);

    double computeCommonRate(Map<Double, Double> bundleDetails);
}
