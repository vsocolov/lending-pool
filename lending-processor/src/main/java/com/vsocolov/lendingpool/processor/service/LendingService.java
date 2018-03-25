package com.vsocolov.lendingpool.processor.service;


import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.data.Lender;

import java.util.List;

public interface LendingService {

    List<Lender> composeLendingBundles(List<LenderRecord> lenderRecords);
}
