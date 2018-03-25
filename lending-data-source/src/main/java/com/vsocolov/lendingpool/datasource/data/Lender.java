package com.vsocolov.lendingpool.datasource.data;

import java.io.Serializable;

public class Lender implements Serializable {

    private final String name;

    private final double rate;

    private final double amount;

    public Lender(final String name, final double rate, final double amount) {
        this.name = name;
        this.rate = rate;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public double getAmount() {
        return amount;
    }
}
