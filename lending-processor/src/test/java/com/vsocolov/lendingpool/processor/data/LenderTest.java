package com.vsocolov.lendingpool.processor.data;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LenderTest {

    private Lender firstLender;

    private Lender secondLender;

    @Before
    public void setUp() {
        firstLender = new Lender("Bob", 0.07, 200);
        secondLender = new Lender("John", 0.05, 300);
    }

    @Test
    public void compareTo_should_return_less_if_rate_is_less_than_comparing_object_rate() {
        assertThat(firstLender.compareTo(secondLender), equalTo(1));
    }

    @Test
    public void compareTo_should_return_less_if_object_has_bundles_and_second_object_has_no_bundles() {
        firstLender.addBundle(new LendingBundle(firstLender, 100.0));

        assertThat(firstLender.compareTo(secondLender), equalTo(-1));
    }

    @Test
    public void removeBundle_should_decrease_amount() {
        final double initialAmount = firstLender.getAmount();
        final LendingBundle bundle = new LendingBundle(firstLender, 10.0);

        firstLender.addBundle(bundle);
        firstLender.removeBundle(); //remove bundle for testing decreasing amount

        assertThat(firstLender.getAmount(), equalTo(initialAmount - bundle.getAmount()));
    }
}