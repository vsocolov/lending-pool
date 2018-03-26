package com.vsocolov.lendingpool.ui.converter;

import com.vsocolov.lendingpool.processor.data.LoanInfo;
import com.vsocolov.lendingpool.ui.data.LoanData;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class LoanInfoToLoanDataConverter implements Converter<LoanInfo, Optional<LoanData>> {

    @Override
    public Optional<LoanData> convert(final LoanInfo source) {
        final LoanData destination = new LoanData();

        destination.setRequestedAmount(doubleToBigDecimal(source.getRequestedAmount(), 0));
        destination.setRate(doubleToBigDecimal(source.getRate() * 100.0, 1));
        destination.setMonthlyRepayment(doubleToBigDecimal(source.getMonthlyRepayment(), 2));
        destination.setTotalRepayment(doubleToBigDecimal(source.getTotalRepayment(), 2));

        return Optional.of(destination);
    }

    private BigDecimal doubleToBigDecimal(final double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
