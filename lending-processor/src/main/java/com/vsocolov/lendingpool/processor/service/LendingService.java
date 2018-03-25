package com.vsocolov.lendingpool.processor.service;


import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;

import java.util.List;
import java.util.Map;

public interface LendingService {

    List<Lender> fetchLendingPool(List<LenderRecord> lenderRecords);

    Map<Double, Double> aggregateLoanBundleDetails(List<Lender> lendingPool, double loanAmount);
}
