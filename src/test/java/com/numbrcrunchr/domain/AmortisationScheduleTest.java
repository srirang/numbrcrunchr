package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class AmortisationScheduleTest {
    @Autowired
    private LoanAmortisationScheduleCalculator calculator;
    private final double loan = 300000;
    private final double interestRate = 7.41;
    private final int termInYears = 30;

    @Test
    public void checkPricipleAndInterestLoanRepayment() {
        AmortisationSchedule amortisationSchedule = calculator
                .calculateAmortisationSchedule(loan, 0, termInYears,
                        interestRate);
        assertEquals(30, amortisationSchedule.getYearlyRepayments().size());

        assertEquals(0, amortisationSchedule.getInterestOnlyRepayments().size());
        assertEquals(361, amortisationSchedule.getMonthlyRepayments().size());
        assertNotNull(amortisationSchedule.getMonthlyRepayments().get(360));
        assertEquals(0, amortisationSchedule.getMonthlyRepayments().get(360)
                .getLoanBalance(), MathUtilTest.ROUNDING_ERROR_TOLERANCE);
    }

    @Test
    public void checkBalances() {
        AmortisationSchedule amortisationSchedule = calculator
                .calculateAmortisationSchedule(loan, termInYears, interestRate);
        assertEquals(30, amortisationSchedule.getYearlyRepayments().size());

        assertEquals(30, amortisationSchedule.getYearlyRepayments().size());
        assertEquals(361, amortisationSchedule.getMonthlyRepayments().size());
        assertNotNull(amortisationSchedule.getMonthlyRepayments().get(360));
        assertEquals(0, amortisationSchedule.getMonthlyRepayments().get(360)
                .getLoanBalance(), MathUtilTest.ROUNDING_ERROR_TOLERANCE);
    }
}
