package com.vsocolov.lendingpool.processor.service;


import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;

import java.util.List;

public interface LendingService {

    List<Lender> fetchLendingPool(List<LenderRecord> lenderRecords);
}
