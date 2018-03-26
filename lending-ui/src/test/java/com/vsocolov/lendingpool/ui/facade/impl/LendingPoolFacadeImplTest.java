package com.vsocolov.lendingpool.ui.facade.impl;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.datasource.services.InputSourceReader;
import com.vsocolov.lendingpool.processor.data.LoanInfo;
import com.vsocolov.lendingpool.processor.service.LendingServiceTemplate;
import com.vsocolov.lendingpool.ui.converter.LoanInfoToLoanDataConverter;
import com.vsocolov.lendingpool.ui.data.LoanData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LendingPoolFacadeImplTest {

    @InjectMocks
    private LendingPoolFacadeImpl facade;

    @Mock
    private InputSourceReader inputSourceReader;

    @Mock
    private LendingServiceTemplate lendingService;

    @Mock
    private LoanInfoToLoanDataConverter loanInfoToLoanDataConverter;

    @Test
    public void calculateLoan_should_calculate_loan_if_lender_records_exist() {
        final String path = "file:/asd";
        final double amount = 1000.0;
        final List<LenderRecord> lenderRecords = Collections.singletonList(mock(LenderRecord.class));
        final Optional<LoanInfo> loanInfo = Optional.of(new LoanInfo(amount, 0.07, 30.1, 1100.1));
        final Optional<LoanData> expectedLoanData = Optional.of(new LoanData());

        when(inputSourceReader.parseLendersSource(any(Path.class))).thenReturn(lenderRecords);
        when(lendingService.fetchLoanInfo(eq(amount), anyList())).thenReturn(loanInfo);
        when(loanInfoToLoanDataConverter.convert(loanInfo.get())).thenReturn(expectedLoanData);

        final InOrder orderCalls = inOrder(inputSourceReader, lendingService, loanInfoToLoanDataConverter);

        final Optional<LoanData> loanData = facade.calculateLoan(path, amount);
        assertThat(loanData, sameInstance(expectedLoanData));
        orderCalls.verify(inputSourceReader).parseLendersSource(any(Path.class));
        orderCalls.verify(lendingService).fetchLoanInfo(eq(amount), anyList());
        orderCalls.verify(loanInfoToLoanDataConverter).convert(loanInfo.get());
    }

    @Test
    public void calculateLoan_should_return_empty_if_fetch_loan_info_has_no_results() {
        final String path = "file:/asd";
        final double amount = 1000.0;

        when(inputSourceReader.parseLendersSource(any(Path.class))).thenReturn(Collections.emptyList());
        when(lendingService.fetchLoanInfo(eq(amount), anyList())).thenReturn(Optional.empty());

        final InOrder orderCalls = inOrder(inputSourceReader, lendingService);

        final Optional<LoanData> loanData = facade.calculateLoan(path, amount);
        assertThat(loanData, equalTo(Optional.empty()));
        orderCalls.verify(inputSourceReader).parseLendersSource(any(Path.class));
        orderCalls.verify(lendingService).fetchLoanInfo(eq(amount), anyList());
        verifyZeroInteractions(loanInfoToLoanDataConverter);
    }
}