package com.vsocolov.lendingpool.ui.validation;

import com.vsocolov.lendingpool.commons.exceptions.LendingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import static com.vsocolov.lendingpool.commons.enums.ExceptionType.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class InputValidatorTest {

    private InputValidatorTemplate validator = new InputValidator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        expectedException.expect(LendingException.class);
        ReflectionTestUtils.setField(validator, "amountMinValue", 1000);
        ReflectionTestUtils.setField(validator, "amountMaxValue", 15000);
        ReflectionTestUtils.setField(validator, "amountIncrementStep", 100);
    }

    @Test
    public void assertArgumentsSize_should_throw_exception_if_arguments_are_less_than_2() {
        expectedException.expectMessage(equalTo(INVALID_NUMBER_OF_ARGUMENTS.getMessage()));

        validator.assertArgumentsSize(new String[]{"path"});
    }

    @Test
    public void assertArgumentTypes_should_throw_exception_if_first_param_is_blank() {
        expectedException.expectMessage(equalTo(PATH_ARGUMENT_INVALID.getMessage()));

        validator.assertArgumentTypes(new String[]{" ", "1000"});
    }

    @Test
    public void assertArgumentTypes_should_throw_exception_if_second_param_is_not_a_number() {
        expectedException.expectMessage(equalTo(AMOUNT_ARGUMENT_INVALID.getMessage()));

        validator.assertArgumentTypes(new String[]{"path", "xx"});
    }

    @Test
    public void assertAmountRange_should_throw_exception_if_amount_is_less_than_min_boundary() {
        expectedException.expectMessage(equalTo(AMOUNT_RANGE_INVALID.getMessage()));

        validator.assertAmountRange(500.0);
    }

    @Test
    public void assertAmountRange_should_throw_exception_if_amount_is_greater_than_max_boundary() {
        expectedException.expectMessage(equalTo(AMOUNT_RANGE_INVALID.getMessage()));

        validator.assertAmountRange(15001);
    }

    @Test
    public void assertAmountIncrement_should_throw_error_if_it_is_not_a_multiple_of_100() {
        expectedException.expectMessage(equalTo(AMOUNT_ARGUMENT_INVALID.getMessage()));
        validator.assertAmountIncrement(5001);
    }
}