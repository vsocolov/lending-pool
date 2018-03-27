package com.vsocolov.lendingpool.ui.validation;

import com.vsocolov.lendingpool.commons.enums.ExceptionType;
import com.vsocolov.lendingpool.commons.exceptions.LendingException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Required;

public class InputValidator extends InputValidatorTemplate {

    private Integer amountMinValue;

    private Integer amountMaxValue;

    private Integer amountIncrementStep;

    @Required
    public void setAmountMinValue(final Integer amountMinValue) {
        this.amountMinValue = amountMinValue;
    }

    @Required
    public void setAmountMaxValue(final Integer amountMaxValue) {
        this.amountMaxValue = amountMaxValue;
    }

    @Required
    public void setAmountIncrementStep(final Integer amountIncrementStep) {
        this.amountIncrementStep = amountIncrementStep;
    }

    @Override
    protected void assertArgumentsSize(final String[] args) {
        if (ArrayUtils.isEmpty(args) || args.length < 2)
            throw new LendingException(ExceptionType.INVALID_NUMBER_OF_ARGUMENTS);
    }

    @Override
    protected void assertArgumentTypes(final String[] args) {
        if (StringUtils.isBlank(args[0]))
            throw new LendingException(ExceptionType.PATH_ARGUMENT_INVALID);

        if (!NumberUtils.isCreatable(args[1]))
            throw new LendingException(ExceptionType.AMOUNT_ARGUMENT_INVALID);
    }

    @Override
    protected void assertAmountRange(final double amount) {
        if (amount < amountMinValue || amount > amountMaxValue)
            throw new LendingException(ExceptionType.AMOUNT_RANGE_INVALID);
    }

    @Override
    protected void assertAmountIncrement(final double amount) {
        if (amount % amountIncrementStep != 0)
            throw new LendingException(ExceptionType.AMOUNT_ARGUMENT_INVALID);
    }
}
