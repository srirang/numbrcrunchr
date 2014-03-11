package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.domain.AmortisationSchedule;
import com.numbrcrunchr.domain.LoanAmortisationScheduleCalculator;
import com.numbrcrunchr.domain.Repayment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class LoanAmortisationScheduleCalculatorTest {

    @Autowired
    private LoanAmortisationScheduleCalculator calculator;

    @Test
    public void checkLoanAmortisationFor300kLoan() {
        long loan = 300000;
        double interest = 7.41;
        int termInYears = 30;
        AmortisationSchedule amortisationSchedule = calculator
                .calculateAmortisationSchedule(loan, termInYears, interest);
        assertEquals(361, amortisationSchedule.getMonthlyRepayments().size());

        // Check first few months repayment
        assertEquals(new Repayment(1, 226.6863403909656, 1852.50, 300000),
                amortisationSchedule.getMonthlyRepayments().get(0));
        assertEquals(new Repayment(2, 228.08612854287935, 1851.1002118480862,
                299773.31365960906), amortisationSchedule
                .getMonthlyRepayments().get(1));

        // Check that at the end of the loan term, balance is 0
        Repayment lastMonthRepayment = amortisationSchedule
                .getMonthlyRepayments().get(
                        amortisationSchedule.getMonthlyRepayments().size() - 1);
        assertTrue(lastMonthRepayment.getInterest().doubleValue() <= 0);
        assertTrue(lastMonthRepayment.getLoanBalance().doubleValue() <= 0);
    }

    @Test
    public void checkLoanAmortisationWithInterestOnlyPeriod() {
        long loan = 300000;
        double interest = 7.41;
        int termInYears = 30;
        int interestOnlyYears = 5;
        AmortisationSchedule amortisationSchedule = calculator
                .calculateAmortisationSchedule(loan, interestOnlyYears,
                        termInYears, interest);
        assertEquals(361, amortisationSchedule.getMonthlyRepayments().size());

        // Check that the repayment for interest only period is the same
        Repayment amortisation = amortisationSchedule.getMonthlyRepayments()
                .get(0);
        for (int i = 1; i < interestOnlyYears; i++) {
            assertEquals(amortisation, amortisationSchedule
                    .getMonthlyRepayments().get(i));
        }

        // Check that at the end of the loan term, balance is 0
        Repayment lastMonthRepayment = amortisationSchedule
                .getMonthlyRepayments().get(
                        amortisationSchedule.getMonthlyRepayments().size() - 1);
        assertTrue(lastMonthRepayment.getInterest().doubleValue() <= 0);
        assertTrue(lastMonthRepayment.getLoanBalance().doubleValue() <= 0);
    }
}
