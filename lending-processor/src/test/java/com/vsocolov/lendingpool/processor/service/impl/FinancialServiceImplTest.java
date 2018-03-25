package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.processor.service.FinancialService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FinancialServiceImplTest {

    private FinancialService service = new FinancialServiceImpl();

    @Test
    public void pmt_should_properly_calculate_monthly_interest() {
        final double pmt = service.pmt(0.07, 36, 1000.0);
        final BigDecimal bigDecimalPmt = BigDecimal.valueOf(pmt).setScale(2, BigDecimal.ROUND_CEILING);

        assertThat(bigDecimalPmt.doubleValue(), equalTo(30.88));
    }

    @Test
    public void computeCommonRate_should_compute_is_bundle_map_is_not_empty() {
        final Map<Double, Double> bundleDetails = new HashMap<Double, Double>() {{
            put(0.07, 600.0);
            put(0.06, 400.0);
        }};

        double rate = service.computeCommonRate(bundleDetails);
        assertThat(rate, equalTo(0.066));
    }

    @Test
    public void computeCommonRate_should_return_minus_one_is_bundle_map_is_empty() {
        final Map<Double, Double> bundleDetails = Collections.emptyMap();

        double rate = service.computeCommonRate(bundleDetails);
        assertThat(rate, equalTo(-1.0));
    }
}