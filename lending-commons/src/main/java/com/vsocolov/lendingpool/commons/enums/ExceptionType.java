package com.vsocolov.lendingpool.commons.enums;

public enum ExceptionType {
    INVALID_NUMBER_OF_ARGUMENTS("Invalid number of arguments."),
    AMOUNT_ARGUMENT_INVALID("Amount argument is invalid."),
    INSUFFICIENT_FUNDS_ON_MARKET("The market does not have sufficient offers from lenders to satisfy the loan."),
    PATH_ARGUMENT_INVALID("Path Argument is empty or invalid."),
    AMOUNT_RANGE_INVALID("Loan amount is out of range.");

    private final String message;

    ExceptionType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
