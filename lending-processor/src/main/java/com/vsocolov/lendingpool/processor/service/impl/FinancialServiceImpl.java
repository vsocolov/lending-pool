package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.processor.service.FinancialService;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

public class FinancialServiceImpl implements FinancialService {
    private static final double MONTHS_IN_YEAR = 12.0;
    private static final double ONE = 1.0;

    @Override
    public double pmt(double yearlyInterestRate, int totalNumberOfMonths, double loanAmount) {
        double rate = yearlyInterestRate / MONTHS_IN_YEAR;
        double denominator = Math.pow((ONE + rate), totalNumberOfMonths) - ONE;

        return (rate + (rate / denominator)) * loanAmount;
    }

    @Override
    public double computeCommonRate(final Map<Double, Double> bundleDetails) {
        if (MapUtils.isEmpty(bundleDetails))
            return -ONE;

        double mergedAmount = bundleDetails.values().stream().mapToDouble(i -> i).sum();

        double mergedRateAmount = bundleDetails.keySet().stream()
                .mapToDouble(rate -> bundleDetails.get(rate) * rate)
                .sum();

        return mergedRateAmount / mergedAmount;
    }
}
