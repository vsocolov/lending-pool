package com.vsocolov.lendingpool.processor.converter;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Every.everyItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LenderRecordToLenderConverterTest {

    private LenderRecordToLenderConverter converter = new LenderRecordToLenderConverter();

    @Before
    public void setUp() {
        converter.setBundleSplitAmount(10);
    }

    @Test
    public void should_convert_properties_and_bundles_if_sufficient_amount() {
        final LenderRecord lenderRecord = mockLenderRecord("John", 110.0, 0.07);

        final Lender lender = converter.convert(lenderRecord);

        assertThat(lender.getName(), equalTo("John"));
        assertThat(lender.getAmount(), equalTo(110.0));
        assertThat(lender.getRate(), equalTo(0.07));
        assertThat(lender.hasBundles(), equalTo(true));
        assertThat(lender.getBundles(), hasSize(11));
        assertThat(lender.getBundles(), everyItem(hasProperty("amount", equalTo(10.0))));
        assertThat(lender.getBundles(), everyItem(hasProperty("lender", equalTo(lender))));
    }

    @Test
    public void should_convert_properties_and_not_compose_bundles_if_lender_has_insufficient_amount() {
        final LenderRecord lenderRecord = mockLenderRecord("John", 5.0, 0.07);

        final Lender lender = converter.convert(lenderRecord);

        assertThat(lender.getName(), equalTo("John"));
        assertThat(lender.getAmount(), equalTo(5.0));
        assertThat(lender.getRate(), equalTo(0.07));
        assertThat(lender.hasBundles(), equalTo(false));
    }

    private LenderRecord mockLenderRecord(final String name, final double amount, final double rate) {
        final LenderRecord record = mock(LenderRecord.class);
        when(record.getName()).thenReturn(name);
        when(record.getAmount()).thenReturn(amount);
        when(record.getRate()).thenReturn(rate);

        return record;
    }

}