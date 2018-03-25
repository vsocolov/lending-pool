package com.vsocolov.lendingpool.commons.enums;

public enum ExceptionType {
    INVALID_NUMBER_OF_ARGUMENTS("Invalid number of arguments."),
    AMOUNT_ARGUMENT_INVALID("Amount argument is invalid.");

    private final String message;

    ExceptionType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
