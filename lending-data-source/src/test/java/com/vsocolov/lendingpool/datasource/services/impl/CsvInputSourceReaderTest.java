package com.vsocolov.lendingpool.datasource.services.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.datasource.converter.RecordToLenderRecordConverter;
import com.vsocolov.lendingpool.datasource.data.CsvLenderRecord;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CsvInputSourceReaderTest {
    private static final String INPUT_SOURCE = "src/test/resources/market.csv";
    private static final String NON_EXISTENT_SOURCE = "src/test/dummy.csv";
    private static final String INVALID_SOURCE_FILE = "src/test/dummy.csv";

    @InjectMocks
    private CsvInputSourceReader reader;

    @Mock
    private RecordToLenderRecordConverter converter;

    @Test
    public void parseLendersSource_should_return_list_of_lenders_if_there_are_lenders_in_source() {
        final Path path = Paths.get(INPUT_SOURCE);

        when(converter.convert(any(CSVRecord.class))).thenReturn(Optional.of(mock(CsvLenderRecord.class)));

        final List<LenderRecord> lenders = reader.parseLendersSource(path);

        assertThat(lenders, hasSize(8));
    }

    @Test
    public void parseLendersSource_should_return_empty_list_if_input_file_does_not_exist() {
        final List<LenderRecord> lenders = reader.parseLendersSource(Paths.get(NON_EXISTENT_SOURCE));

        assertThat(lenders, is(empty()));
    }

    @Test
    public void parseLendersSource_should_return_empty_list_if_input_file_is_invalid() {
        final List<LenderRecord> lenders = reader.parseLendersSource(Paths.get(INVALID_SOURCE_FILE));

        assertThat(lenders, is(empty()));
    }
}