package com.vsocolov.lendingpool.processor.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Lender implements Serializable, Comparable<Lender> {

    private final String name;

    private final double rate;

    private double amount;

    private final Queue<LendingBundle> bundles;

    public Lender(String name, double rate, double amount) {
        this.name = name;
        this.rate = rate;
        this.amount = amount;
        this.bundles = new LinkedList<>();
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

    public Queue<LendingBundle> getBundles() {
        return bundles;
    }

    public void addBundle(final LendingBundle bundle) {
        bundles.add(bundle);
    }

    public LendingBundle removeBundle() {
        final LendingBundle bundle = bundles.poll();
        this.amount -= bundle.getAmount();

        return bundle;
    }

    public boolean hasBundles() {
        return !bundles.isEmpty();
    }

    @Override
    public int compareTo(final Lender that) {
        if ((!this.hasBundles() && that.hasBundles()))
            return 1;

        if (this.rate < that.rate || (this.hasBundles() && !that.hasBundles()))
            return -1;

        return 1;
    }
}
