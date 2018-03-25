package com.vsocolov.lendingpool.datasource.data;

import com.vsocolov.lendingpool.commons.data.LenderRecord;

public class CsvLenderRecord implements LenderRecord {

    private final String name;

    private final double rate;

    private final double amount;

    public CsvLenderRecord(final String name, final double rate, final double amount) {
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
