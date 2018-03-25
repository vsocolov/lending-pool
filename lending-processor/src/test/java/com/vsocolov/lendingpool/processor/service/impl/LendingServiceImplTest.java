package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.service.BundleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LendingServiceImplTest {

    @InjectMocks
    private LendingServiceImpl service = new LendingServiceImpl();

    @Mock
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Mock
    private BundleService bundleService;

    @Test
    public void fetchLendingPool_should_call_converter_and_return_lender_list() {
        final LenderRecord lenderRecord = mock(LenderRecord.class);
        final List<LenderRecord> lenderRecords = Collections.singletonList(lenderRecord);
        final Lender expectedLender = new Lender("John", 0.07, 1000.0);

        when(lenderRecordToLenderConverter.convert(lenderRecord)).thenReturn(expectedLender);

        final List<Lender> lenders = service.fetchLendingPool(lenderRecords);
        assertThat(lenders, hasSize(1));
        assertThat(lenders.get(0), sameInstance(expectedLender));
    }

    @Test
    public void fetchLendingPool_should_not_call_converter_if_record_list_is_empty() {
        final List<Lender> lenders = service.fetchLendingPool(Collections.emptyList());

        assertThat(lenders, is(empty()));
        verifyZeroInteractions(lenderRecordToLenderConverter);
    }

    @Test
    public void aggregateLoanBundleDetails_should_aggregate_bundle_details_if_bundle_exists() {
        final List<Lender> lendingPool = Collections.emptyList();
        final double loanAmount = 30.0;

        when(bundleService.findLendingBundles(lendingPool, loanAmount)).thenReturn(stubLendingBundles());

        final Map<Double, Double> result = service.aggregateLoanBundleDetails(lendingPool, loanAmount);
        assertThat(result.get(0.07), equalTo(20.0));
        assertThat(result.get(0.04), equalTo(10.0));
    }

    private List<LendingBundle> stubLendingBundles() {
        final Lender firstLender = new Lender("John", 0.07, 20.0);
        final Lender secondLender = new Lender("Bob", 0.04, 10.0);

        final LendingBundle firstBundle = new LendingBundle(firstLender, 10.0);
        final LendingBundle secondBundle = new LendingBundle(firstLender, 10.0);
        final LendingBundle thirdBundle = new LendingBundle(secondLender, 10.0);

        return Arrays.asList(firstBundle, secondBundle, thirdBundle);
    }
}