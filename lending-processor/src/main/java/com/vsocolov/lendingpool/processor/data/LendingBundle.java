package com.vsocolov.lendingpool.processor.data;

import java.io.Serializable;

public class LendingBundle implements Serializable {

    private final Lender lender;

    private final double amount;

    public LendingBundle(final Lender lender, final double amount) {
        this.lender = lender;
        this.amount = amount;
    }

    public Lender getLender() {
        return lender;
    }

    public double getAmount() {
        return amount;
    }
}
