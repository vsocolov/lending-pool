package com.vsocolov.lendingpool.ui;

import com.vsocolov.lendingpool.ui.data.LoanData;
import com.vsocolov.lendingpool.ui.facade.LendingPoolFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

public class AppEntryPoint {
    private static final String APP_CONTEXT_FILE = "ui-spring-beans.xml";

    public static void main(final String[] args) {
        final ApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);

        final LendingPoolFacade facade = ctx.getBean(LendingPoolFacade.class);

        facade.validateInputs(args);

        final Optional<LoanData> loanInfo = facade.calculateLoan(args[0], Double.valueOf(args[1]));

        loanInfo.ifPresent(System.out::println);
    }

}
