package au.com.numbrcrunchr.domain;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;

public class FinancialYearUtils {

	public static String getTaxYear(Date aDate) {
		DateTime aDateTime = new DateTime(aDate);
		int currentYear = aDateTime.year().get();
		int previousYear = currentYear - 1;
		int nextYear = currentYear + 1;
		if (aDateTime.getMonthOfYear() >= Calendar.JULY + 1) {
			new DateMidnight(nextYear, Calendar.JUNE, 30);
			return currentYear + "-" + nextYear;
		} else {
			new DateMidnight(currentYear, Calendar.JUNE, 30);
			return previousYear + "-" + currentYear;
		}
	}

	public static Date getEndOfFinancialYear(Date currentDate) {
		return getEndOfFinancialYearMidnight(currentDate).toDate();
	}

	protected static DateMidnight getEndOfFinancialYearMidnight(Date currentDate) {
		DateTime aDateTime = new DateTime(currentDate);
		int currentYear = aDateTime.year().get();
		int nextYear = currentYear + 1;
		if (aDateTime.getMonthOfYear() >= Calendar.JULY + 1) {
			return new DateMidnight(nextYear, DateTimeConstants.JUNE, 30);
		} else {
			return new DateMidnight(currentYear, DateTimeConstants.JUNE, 30);
		}
	}

	protected static DateMidnight getBeginningOfFinancialYearMidnight(
			Date currentDate) {
		DateMidnight eofy = getEndOfFinancialYearMidnight(currentDate);
		int year = eofy.year().get() - 1;
		int month = DateTimeConstants.JULY;
		int day = 1;
		return new DateMidnight(year, month, day);
	}

	public static PartialPeriodDetails getFinancialYearRemainingOn(
			Date currentDate) {
		DateMidnight endOfFinancialYear = getEndOfFinancialYearMidnight(currentDate);
		Days fullFy = Days.daysBetween(
				getBeginningOfFinancialYearMidnight(currentDate),
				endOfFinancialYear);
		Days remainingFy = Days.daysBetween(new DateMidnight(currentDate),
				endOfFinancialYear);
		double proRataFy = (double) remainingFy.getDays()
				/ (double) fullFy.getDays();
		PartialPeriodDetails partialPeriodDetails = new PartialPeriodDetails(
				proRataFy, endOfFinancialYear.toDate());
		return partialPeriodDetails;
	}

	public static double getFinancialYearRatioRemainingOn(Date currentDate) {
		return getFinancialYearRemainingOn(currentDate)
				.getProRataFinancialYear();
	}
}
