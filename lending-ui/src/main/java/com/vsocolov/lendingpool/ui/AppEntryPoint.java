package com.vsocolov.lendingpool.ui;

import com.vsocolov.lendingpool.ui.facade.LendingPoolFacade;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppEntryPoint {
    private static final String APP_CONTEXT_FILE = "ui-spring-beans.xml";

    public static void main(final String[] args) {
        assertInputs(args);

        final ApplicationContext ctx = new ClassPathXmlApplicationContext(APP_CONTEXT_FILE);

        final LendingPoolFacade facade = ctx.getBean(LendingPoolFacade.class);
        facade.getLenders(args[0])
                .forEach(lender -> System.out.println(lender.getName()));
    }

    private static void assertInputs(final String[] args) {
        if (args.length < 2)
            throw new RuntimeException("Invalid number of arguments.");

        if (!NumberUtils.isCreatable(args[1]))
            throw new RuntimeException("Amount argument is invalid.");
    }

}
