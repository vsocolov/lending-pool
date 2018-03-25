package com.vsocolov.lendingpool.datasource.converter;

import com.vsocolov.lendingpool.datasource.data.CsvLenderRecord;
import com.vsocolov.lendingpool.datasource.enums.CsvSourceHeader;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CSVRecord.class)
public class RecordToLenderRecordConverterTest {

    private RecordToLenderRecordConverter converter = new RecordToLenderRecordConverter();

    private CSVRecord record;

    @Before
    public void setUp() {
        record = mock(CSVRecord.class);
    }

    @Test
    public void should_convert_object_if_record_is_valid() {
        when(record.isConsistent()).thenReturn(true);
        when(record.isMapped(any(String.class))).thenReturn(true);
        when(record.get(CsvSourceHeader.Lender)).thenReturn("John");
        when(record.get(CsvSourceHeader.Available)).thenReturn("640.0");
        when(record.get(CsvSourceHeader.Rate)).thenReturn("0.075");

        final Optional<CsvLenderRecord> lender = converter.convert(record);

        assertThat(lender.get().getName(), equalTo("John"));
        assertThat(lender.get().getAmount(), equalTo(640.0));
        assertThat(lender.get().getRate(), equalTo(0.075));
        verify(record, times(3)).isMapped(any(String.class));
    }

    @Test
    public void should_return_optional_empty_if_record_is_not_valid() {
        when(record.isConsistent()).thenReturn(false);
        when(record.isMapped(any(String.class))).thenReturn(false);

        final Optional<CsvLenderRecord> lender = converter.convert(record);
        assertThat(lender, equalTo(Optional.empty()));
    }

    @Test
    public void should_return_optional_empty_if_number_records_are_not_numbers() {
        when(record.isConsistent()).thenReturn(true);
        when(record.isMapped(any(String.class))).thenReturn(true);
        when(record.get(CsvSourceHeader.Lender)).thenReturn("John");
        when(record.get(CsvSourceHeader.Available)).thenReturn("text");
        when(record.get(CsvSourceHeader.Rate)).thenReturn("text");

        final Optional<CsvLenderRecord> lender = converter.convert(record);

        assertThat(lender, equalTo(Optional.empty()));
        verify(record, times(3)).isMapped(any(String.class));
        verify(record, times(3)).get(any(CsvSourceHeader.class));
    }
}