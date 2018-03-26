package com.vsocolov.lendingpool.processor.data;

import java.io.Serializable;

public class LoanInfo implements Serializable {

    private final double requestedAmount;

    private final double rate;

    private final double monthlyRepayment;

    private final double totalRepayment;

    public LoanInfo(final double requestedAmount, final double rate,
                    final double monthlyRepayment, final double totalRepayment) {
        this.requestedAmount = requestedAmount;
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getRate() {
        return rate;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }
}
