package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateMidnight;
import org.junit.Test;

public class FinancialYearUtilsTest {

	@Test
	public void checkTaxYear() {
		assertEquals("2010-2011",
				FinancialYearUtils.getTaxYear(new DateMidnight(2010, 8, 11)
						.toDate()));
		assertEquals("2009-2010",
				FinancialYearUtils.getTaxYear(new DateMidnight(2010, 1, 11)
						.toDate()));
		assertEquals("2009-2010",
				FinancialYearUtils.getTaxYear(new DateMidnight(2010, 6, 10)
						.toDate()));
		assertEquals("2009-2010",
				FinancialYearUtils.getTaxYear(new DateMidnight(2010, 6, 30)
						.toDate()));
		assertEquals("2010-2011",
				FinancialYearUtils.getTaxYear(new DateMidnight(2010, 7, 1)
						.toDate()));
	}

	@Test
	public void checkFinancialYearEndDate() {
		assertEquals("Thu Jun 30 00:00:00 EST 2011", FinancialYearUtils
				.getEndOfFinancialYear(new DateMidnight(2010, 8, 11).toDate())
				.toString());
		assertEquals("Wed Jun 30 00:00:00 EST 2010", FinancialYearUtils
				.getEndOfFinancialYear(new DateMidnight(2010, 1, 11).toDate())
				.toString());
	}

	@Test
	public void checkFinancialYearRatio() {
		assertEquals(1,
				FinancialYearUtils
						.getFinancialYearRatioRemainingOn(new DateMidnight(2010, 7,
								1).toDate()), 0);
		assertEquals(0,
				FinancialYearUtils
						.getFinancialYearRatioRemainingOn(new DateMidnight(2010, 6,
								30).toDate()), 0);
		assertEquals(0.054945054945054944,
				FinancialYearUtils
						.getFinancialYearRatioRemainingOn(new DateMidnight(2010, 6,
								10).toDate()), 0);
		assertEquals(0.07967032967032966,
				FinancialYearUtils
						.getFinancialYearRatioRemainingOn(new DateMidnight(2010, 6,
								1).toDate()), 0);
		assertEquals(0.08241758241758242,
				FinancialYearUtils
						.getFinancialYearRatioRemainingOn(new DateMidnight(2010, 5,
								31).toDate()), 0);
	}
}
