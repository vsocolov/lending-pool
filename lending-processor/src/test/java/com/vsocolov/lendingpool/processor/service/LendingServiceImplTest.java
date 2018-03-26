package com.vsocolov.lendingpool.processor.service;

import com.vsocolov.lendingpool.commons.data.LenderRecord;
import com.vsocolov.lendingpool.processor.converter.LenderRecordToLenderConverter;
import com.vsocolov.lendingpool.processor.data.Lender;
import com.vsocolov.lendingpool.processor.data.LendingBundle;
import com.vsocolov.lendingpool.processor.data.LoanInfo;
import com.vsocolov.lendingpool.processor.service.impl.LendingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LendingServiceImplTest {

    @InjectMocks
    @Spy
    private LendingServiceTemplate service = new LendingServiceImpl();

    @Mock
    private LenderRecordToLenderConverter lenderRecordToLenderConverter;

    @Mock
    private BundleService bundleService;

    @Mock
    private FinancialService financialService;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "lendingPeriodMonths", 36);
    }

    @Test
    public void fetchLendingPool_should_call_converter_and_return_lender_list() {
        final LenderRecord lenderRecord = mock(LenderRecord.class);
        final List<LenderRecord> lenderRecords = Collections.singletonList(lenderRecord);
        final Lender expectedLender = new Lender("John", 0.07, 1000.0);

        when(lenderRecordToLenderConverter.convert(lenderRecord)).thenReturn(expectedLender);

        final List<Lender> lenders = service.fetchLendingPool(lenderRecords);
        assertThat(lenders, hasSize(1));
        assertThat(lenders.get(0), sameInstance(expectedLender));
    }

    @Test
    public void fetchLendingPool_should_not_call_converter_if_record_list_is_empty() {
        final List<Lender> lenders = service.fetchLendingPool(Collections.emptyList());

        assertThat(lenders, is(empty()));
        verifyZeroInteractions(lenderRecordToLenderConverter);
    }

    @Test
    public void aggregateLoanBundleDetails_should_aggregate_bundle_details_if_bundle_exists() {
        final List<Lender> lendingPool = Collections.emptyList();
        final double loanAmount = 30.0;

        when(bundleService.findLendingBundles(lendingPool, loanAmount)).thenReturn(stubLendingBundles());

        final Map<Double, Double> result = service.aggregateLoanBundleDetails(lendingPool, loanAmount);
        assertThat(result.get(0.07), equalTo(20.0));
        assertThat(result.get(0.04), equalTo(10.0));
    }

    @Test
    public void calculateLoan_should_properly_calculate_loan_if_bundles_exist() {
        final double loanAmount = 1000.0;
        final double yearlyInterestRate = 0.066;
        final double monthlyRepayment = 30.1;
        final Map<Double, Double> loanBundleDetails = new HashMap<Double, Double>() {{
            put(0.07, 600.0);
            put(0.06, 400.0);
        }};

        when(financialService.computeCommonRate(loanBundleDetails)).thenReturn(yearlyInterestRate);
        when(financialService.pmt(yearlyInterestRate, 36, loanAmount)).thenReturn(monthlyRepayment);

        final Optional<LoanInfo> loanInfo = service.calculateLoan(loanBundleDetails, loanAmount);
        assertThat(loanInfo.isPresent(), equalTo(true));
        assertThat(loanInfo.get().getRequestedAmount(), equalTo(loanAmount));
        assertThat(loanInfo.get().getRate(), equalTo(yearlyInterestRate));
        assertThat(loanInfo.get().getMonthlyRepayment(), equalTo(monthlyRepayment));
        final BigDecimal bigDecimalRepayment = BigDecimal.valueOf(loanInfo.get().getTotalRepayment())
                .setScale(2, BigDecimal.ROUND_CEILING);
        assertThat(bigDecimalRepayment.doubleValue(), equalTo(1083.61));
    }

    @Test
    public void calculateLoan_should_return_optional_empty_if_bundles_not_exists() {
        final Map<Double, Double> loanBundleDetails = Collections.emptyMap();

        final Optional<LoanInfo> loanInfo = service.calculateLoan(loanBundleDetails, 1000.0);

        assertThat(loanInfo, equalTo(Optional.empty()));
    }

    @Test
    public void fetchLoanInfo_should_call_template_methods_in_proper_order_and_return_result() {
        final double loanAmount = 1000.0;
        final List<LenderRecord> lenderRecords = Collections.emptyList();
        final Optional<LoanInfo> expectedLoanInfo = Optional.of(new LoanInfo(loanAmount, 0.07, 30.0, 1100.0));

        doReturn(Collections.emptyList()).when(service).fetchLendingPool(lenderRecords);
        doReturn(Collections.emptyMap()).when(service).aggregateLoanBundleDetails(anyList(), eq(loanAmount));
        doReturn(expectedLoanInfo).when(service).calculateLoan(anyMap(), eq(loanAmount));

        final InOrder inOrderCalls = inOrder(service);

        final Optional<LoanInfo> loanInfo = service.fetchLoanInfo(loanAmount, lenderRecords);

        assertThat(loanInfo.isPresent(), equalTo(true));
        assertThat(loanInfo, sameInstance(expectedLoanInfo));
        inOrderCalls.verify(service).fetchLendingPool(lenderRecords);
        inOrderCalls.verify(service).aggregateLoanBundleDetails(anyList(), eq(loanAmount));
        inOrderCalls.verify(service).calculateLoan(anyMap(), eq(loanAmount));
    }


    private List<LendingBundle> stubLendingBundles() {
        final Lender firstLender = new Lender("John", 0.07, 20.0);
        final Lender secondLender = new Lender("Bob", 0.04, 10.0);

        final LendingBundle firstBundle = new LendingBundle(firstLender, 10.0);
        final LendingBundle secondBundle = new LendingBundle(firstLender, 10.0);
        final LendingBundle thirdBundle = new LendingBundle(secondLender, 10.0);

        return Arrays.asList(firstBundle, secondBundle, thirdBundle);
    }
}