package com.vsocolov.lendingpool.ui.validation;

public abstract class InputValidatorTemplate {

    protected abstract void assertArgumentsSize(String[] args);

    protected abstract void assertArgumentTypes(String[] args);

    protected abstract void assertAmountRange(double amount);

    protected abstract void assertAmountIncrement(double amount);

    public void validate(final String[] args) {
        assertArgumentsSize(args);
        assertArgumentTypes(args);

        final Double loanAmount = Double.valueOf(args[1]);
        assertAmountRange(loanAmount);
        assertAmountIncrement(loanAmount);
    }
}
