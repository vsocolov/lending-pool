package com.vsocolov.lendingpool.datasource.services.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.datasource.converter.RecordToLenderRecordConverter;
import com.vsocolov.lendingpool.datasource.enums.CsvSourceHeader;
import com.vsocolov.lendingpool.datasource.services.InputSourceReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvInputSourceReader implements InputSourceReader {
    private static final Logger LOG = Logger.getLogger(CsvInputSourceReader.class);

    @Autowired
    private RecordToLenderRecordConverter converter;

    @Override
    public List<LenderRecord> parseLendersSource(final Path path) {
        try {
            try (final Reader reader = new FileReader(path.toFile())) {
                final Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withHeader(CsvSourceHeader.stringValues()).parse(reader);

                return StreamSupport.stream(records.spliterator(), false)
                        .map(record -> converter.convert(record))
                        .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            LOG.error("Cannot read input source file.", e);
        }

        return Collections.emptyList();
    }
}
