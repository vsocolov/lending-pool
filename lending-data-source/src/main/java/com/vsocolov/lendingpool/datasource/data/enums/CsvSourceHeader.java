package com.vsocolov.lendingpool.datasource.data.enums;

import java.util.Arrays;

public enum CsvSourceHeader {
    Lender,
    Rate,
    Available;

    public static String[] stringValues() {
        return Arrays.stream(CsvSourceHeader.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
