package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.service.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.stream.Collectors;

public class LendingServiceImpl implements LendingService {

    private Integer lendingPeriod;

    @Autowired
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Required
    protected void setLendingPeriod(final Integer lendingPeriod) {
        this.lendingPeriod = lendingPeriod;
    }

    @Override
    public List<Lender> composeLendingBundles(final List<LenderRecord> lenderRecords) {
        return lenderRecords.stream()
                .map(record -> lenderRecordToLenderConverter.convert(record))
                .collect(Collectors.toList());
    }
}
