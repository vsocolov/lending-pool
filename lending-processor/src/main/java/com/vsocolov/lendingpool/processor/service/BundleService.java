package com.vsocolov.lendingpool.processor.service;

import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;

import java.util.List;

public interface BundleService {

    List<LendingBundle> findLendingBundles(List<Lender> lendingPool, double loanAmount);
}
