package com.vsocolov.lendingpool.ui.converter;

import com.vsocolov.lendingpool.processor.data.LoanInfo;
import com.vsocolov.lendingpool.ui.data.LoanData;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoanInfoToLoanDataConverterTest {

    private LoanInfoToLoanDataConverter converter = new LoanInfoToLoanDataConverter();

    @Test
    public void convert_should_convert_and_scale_values_to_bigdecimal() {
        final LoanInfo loanInfo = new LoanInfo(1000.0, 0.071, 31.123, 1108.417);

        final Optional<LoanData> result = converter.convert(loanInfo);

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get().getRequestedAmount(), equalTo(BigDecimal.valueOf(1000)));
        assertThat(result.get().getMonthlyRepayment(), equalTo(BigDecimal.valueOf(31.12)));
        assertThat(result.get().getTotalRepayment(), equalTo(BigDecimal.valueOf(1108.42)));
        assertThat(result.get().getRate(), equalTo(BigDecimal.valueOf(7.1)));
    }
}
