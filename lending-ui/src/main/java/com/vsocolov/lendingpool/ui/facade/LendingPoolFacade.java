package com.vsocolov.lendingpool.ui.facade;

import com.vsocolov.lendingpool.datasource.data.Lender;
import com.vsocolov.lendingpool.ui.data.LoanData;

import java.util.List;
import java.util.Optional;

public interface LendingPoolFacade {

    Optional<LoanData> calculateLoan(String path, double amount);

    List<Lender> getLenders(String path);
}
