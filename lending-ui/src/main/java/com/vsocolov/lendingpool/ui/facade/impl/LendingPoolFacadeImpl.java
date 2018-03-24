package com.vsocolov.lendingpool.ui.facade.impl;

import com.vsocolov.lendingpool.datasource.data.Lender;
import com.vsocolov.lendingpool.datasource.services.InputSourceReader;
import com.vsocolov.lendingpool.ui.data.LoanData;
import com.vsocolov.lendingpool.ui.facade.LendingPoolFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class LendingPoolFacadeImpl implements LendingPoolFacade {

    @Autowired
    private InputSourceReader inputSourceReader;

    @Override
    public Optional<LoanData> calculateLoan(String path, double amount) {
        return Optional.empty();
    }

    @Override
    public List<Lender> getLenders(final String path) {
        return inputSourceReader.parseLendersSource(Paths.get(path));
    }
}
