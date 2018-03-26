package com.vsocolov.lendingpool.ui.facade.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.datasource.services.InputSourceReader;
import com.vsocolov.lendingpool.processor.service.LendingServiceTemplate;
import com.vsocolov.lendingpool.ui.converter.LoanInfoToLoanDataConverter;
import com.vsocolov.lendingpool.ui.facade.LendingPoolFacade;
import com.vsocolov.lendingpool.ui.data.LoanData;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class LendingPoolFacadeImpl implements LendingPoolFacade {

    @Autowired
    private InputSourceReader inputSourceReader;

    @Autowired
    private LendingServiceTemplate lendingService;

    @Autowired
    private LoanInfoToLoanDataConverter loanInfoToLoanDataConverter;

    @Override
    public Optional<LoanData> calculateLoan(String path, double amount) {
        final List<LenderRecord> lenderRecords = inputSourceReader.parseLendersSource(Paths.get(path));

        return lendingService.fetchLoanInfo(amount, lenderRecords)
                .flatMap(loanInfo -> loanInfoToLoanDataConverter.convert(loanInfo));
    }
}
