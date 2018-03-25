package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.exceptions.LendingException;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.service.BundleService;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BundleServiceImplTest {

    private BundleService service = new BundleServiceImpl();

    @Test
    public void findLendingBundles_should_return_bundles_if_there_is_sufficient_amount_in_pool() {
        final Lender firstLender = new Lender("John", 0.7, 10.0);
        final LendingBundle firstLenderBundle = new LendingBundle(firstLender, 10.0);
        firstLender.addBundle(firstLenderBundle);
        final Lender secondLender = new Lender("Bob", 0.4, 10.0);
        final LendingBundle secondLenderBundle = new LendingBundle(secondLender, 10.0);
        secondLender.addBundle(secondLenderBundle);
        final Lender thirdLender = new Lender("Alice", 0.6, 10.0);
        final LendingBundle thirdLenderBundle = new LendingBundle(thirdLender, 10.0);
        thirdLender.addBundle(thirdLenderBundle);

        final List<Lender> lendingPool = Arrays.asList(firstLender, secondLender, thirdLender);

        final List<LendingBundle> lendingBundles = service.findLendingBundles(lendingPool, 20.0);

        assertThat(lendingBundles, hasSize(2));
        assertThat(lendingBundles, hasItems(sameInstance(secondLenderBundle), sameInstance(thirdLenderBundle)));
    }

    @Test
    public void findLendingBundles_should_compute_bundles_recursive_in_pool_if_amount_is_still_not_found() {
        final Lender firstLender = new Lender("John", 0.7, 10.0);
        final LendingBundle firstLenderBundle1 = new LendingBundle(firstLender, 10.0);
        final LendingBundle firstLenderBundle2 = new LendingBundle(firstLender, 10.0);
        firstLender.addBundle(firstLenderBundle1);
        firstLender.addBundle(firstLenderBundle2);
        final Lender secondLender = new Lender("Bob", 0.4, 10.0);
        final LendingBundle secondLenderBundle = new LendingBundle(secondLender, 10.0);
        secondLender.addBundle(secondLenderBundle);
        final List<Lender> lendingPool = Arrays.asList(firstLender, secondLender);

        final List<LendingBundle> lendingBundles = service.findLendingBundles(lendingPool, 30.0);

        assertThat(lendingBundles, hasSize(3));
        assertThat(lendingBundles, hasItems(sameInstance(secondLenderBundle), sameInstance(firstLenderBundle1),
                sameInstance(firstLenderBundle2)));
    }

    @Test(expected = LendingException.class)
    public void findLendingBundles_should_throw_exception_if_there_are_no_sufficient_bundles() {
        final Lender firstLender = new Lender("John", 0.7, 10.0);
        final LendingBundle firstLenderBundle = new LendingBundle(firstLender, 10.0);
        firstLender.addBundle(firstLenderBundle);

        final List<Lender> lendingPool = Collections.singletonList(firstLender);

        service.findLendingBundles(lendingPool, 20.0);
    }
}