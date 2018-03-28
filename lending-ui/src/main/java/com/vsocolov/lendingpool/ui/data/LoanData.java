package com.vsocolov.lendingpool.ui.data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class LoanData {
    private BigDecimal requestedAmount;

    private BigDecimal rate;

    private BigDecimal monthlyRepayment;

    private BigDecimal totalRepayment;

    public LoanData() {
        super();
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(final BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(final BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(final BigDecimal monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

    public void setTotalRepayment(final BigDecimal totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    @Override
    public String toString() {
        final String currencySymbol = Currency.getInstance(Locale.UK).getSymbol(Locale.UK);
        final StringBuilder sb = new StringBuilder()
                .append("\nRequested amount: ").append(currencySymbol).append(requestedAmount)
                .append("\nRate: ").append(rate).append("%")
                .append("\nMonthly repayment: ").append(currencySymbol).append(monthlyRepayment)
                .append("\nTotal repayment: ").append(currencySymbol).append(totalRepayment)
                .append("\n");
        return sb.toString();
    }
}
