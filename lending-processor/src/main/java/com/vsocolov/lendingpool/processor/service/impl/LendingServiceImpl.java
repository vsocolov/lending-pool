package com.vsocolov.lendingpool.processor.service.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.service.BundleService;
import com.vsocolov.lendingpool.processor.service.LendingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class LendingServiceImpl implements LendingService {

    @Autowired
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Autowired
    private BundleService bundleService;

    @Override
    public List<Lender> fetchLendingPool(final List<LenderRecord> lenderRecords) {
        return lenderRecords.stream()
                .map(record -> lenderRecordToLenderConverter.convert(record))
                .collect(Collectors.toList());
    }
}
