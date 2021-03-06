package com.vsocolov.lendingpool.commons.exceptions;

import com.vsocolov.lendingpool.commons.enums.ExceptionType;

public class LendingException extends RuntimeException {

    public LendingException(final ExceptionType exceptionType) {
        super(exceptionType.getMessage());
    }

}
