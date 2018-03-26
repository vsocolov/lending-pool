package com.vsocolov.lendingpool.ui;

import com.vsocolov.lendingpool.commons.enums.ExceptionType;
import com.vsocolov.lendingpool.commons.exceptions.LendingException;
import com.vsocolov.lendingpool.ui.facade.LendingPoolFacade;
import com.vsocolov.lendingpool.ui.data.LoanData;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

public class AppEntryPoint {
    private static final String APP_CONTEXT_FILE = "ui-spring-beans.xml";

    public static void main(final String[] args) {
        assertInputs(args);

        final ApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);

        final LendingPoolFacade facade = ctx.getBean(LendingPoolFacade.class);

        final Optional<LoanData> loanInfo = facade.calculateLoan(args[0], Double.valueOf(args[1]));

        loanInfo.ifPresent(System.out::println);
    }

    private static void assertInputs(final String[] args) {
        if (args.length < 2)
            throw new LendingException(ExceptionType.INVALID_NUMBER_OF_ARGUMENTS);

        if (!NumberUtils.isCreatable(args[1]))
            throw new LendingException(ExceptionType.AMOUNT_ARGUMENT_INVALID);
    }

}
