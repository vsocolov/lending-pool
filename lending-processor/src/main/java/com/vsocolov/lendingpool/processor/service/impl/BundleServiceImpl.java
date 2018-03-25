package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.enums.ExceptionType;
import com.vsocolov.lendingpool.commons.exceptions.LendingException;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.service.BundleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BundleServiceImpl implements BundleService {

    @Override
    public List<LendingBundle> findLendingBundles(final List<Lender> lendingPool, final double loanAmount) {
        Collections.sort(lendingPool); // sort by rate and existing bundles
        return doFindBundleRecursively(lendingPool, loanAmount);
    }

    private List<LendingBundle> doFindBundleRecursively(final List<Lender> lendingPool, final double loanAmount) {
        final List<LendingBundle> lendingBundles = new ArrayList<>();

        double amount = loanAmount;
        for (final Lender lender : lendingPool) {
            if (amount > 0 && lender.hasBundles()) {
                final LendingBundle bundle = lender.removeBundle();
                amount -= bundle.getAmount();
                lendingBundles.add(bundle);
            }
        }

        if (isLendingPoolEmpty(lendingPool) && amount > 0)
            throw new LendingException(ExceptionType.INSUFFICIENT_FUNDS_ON_MARKET);

        if (!isLendingPoolEmpty(lendingPool) && amount > 0) {
            lendingBundles.addAll(doFindBundleRecursively(lendingPool, amount));
        }

        return lendingBundles;
    }

    private boolean isLendingPoolEmpty(final List<Lender> lendingPool) {
        return lendingPool.stream().noneMatch(Lender::hasBundles);
    }
}
