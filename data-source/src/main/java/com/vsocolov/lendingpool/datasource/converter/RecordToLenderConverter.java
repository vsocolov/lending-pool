package com.vsocolov.lendingpool.datasource.converter;

import com.vsocolov.lendingpool.datasource.data.Lender;
import com.vsocolov.lendingpool.datasource.data.enums.CsvSourceHeader;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class RecordToLenderConverter implements Converter<CSVRecord, Optional<Lender>> {

    @Override
    public Optional<Lender> convert(final CSVRecord record) {

        if (isValidRecord(record)) {
            final String name = record.get(CsvSourceHeader.Lender);
            final String rate = record.get(CsvSourceHeader.Rate);
            final String amount = record.get(CsvSourceHeader.Available);
            if (StringUtils.isNotBlank(name) && NumberUtils.isCreatable(rate) && NumberUtils.isCreatable(amount)) {
                return Optional.of(new Lender(name, Double.valueOf(rate), Double.valueOf(amount)));
            }
        }

        return Optional.empty();
    }


    private boolean isValidRecord(final CSVRecord record) {
        return record != null && record.isConsistent()
                && record.isMapped(CsvSourceHeader.Lender.name())
                && record.isMapped(CsvSourceHeader.Rate.name())
                && record.isMapped(CsvSourceHeader.Available.name());
    }
}
