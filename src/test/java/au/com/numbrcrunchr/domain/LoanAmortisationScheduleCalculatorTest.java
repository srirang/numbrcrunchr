package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
		assertEquals(361, amortisationSchedule.getMonthlyAmortisations().size());

		// Check first few months repayment
		assertEquals(new Amortisation(1, 226.69, 1852.50, 300000),
				amortisationSchedule.getMonthlyAmortisations().get(0));
		assertEquals(new Amortisation(2, 228.09, 1851.10, 299773.31),
				amortisationSchedule.getMonthlyAmortisations().get(1));

		// Check that at the end of the loan term, balance is 0
		Amortisation lastMonthAmortisation = amortisationSchedule
				.getMonthlyAmortisations()
				.get(amortisationSchedule.getMonthlyAmortisations().size() - 1);
		assertTrue(lastMonthAmortisation.getInterest().doubleValue() <= 0);
		assertTrue(lastMonthAmortisation.getLoanBalance().doubleValue() <= 0);
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
		assertEquals(361, amortisationSchedule.getMonthlyAmortisations().size());

		// Check that the repayment for interest only period is the same
		Amortisation amortisation = amortisationSchedule
				.getMonthlyAmortisations().get(0);
		for (int i = 1; i < interestOnlyYears; i++) {
			assertEquals(amortisation, amortisationSchedule
					.getMonthlyAmortisations().get(i));
		}

		// Check that at the end of the loan term, balance is 0
		Amortisation lastMonthAmortisation = amortisationSchedule
				.getMonthlyAmortisations()
				.get(amortisationSchedule.getMonthlyAmortisations().size() - 1);
		assertTrue(lastMonthAmortisation.getInterest().doubleValue() <= 0);
		assertTrue(lastMonthAmortisation.getLoanBalance().doubleValue() <= 0);
	}
}