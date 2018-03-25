package com.vsocolov.lendingpool.commons.data;

import java.io.Serializable;

public interface LenderRecord extends Serializable{

    String getName();

    double getRate();

    double getAmount();
}
