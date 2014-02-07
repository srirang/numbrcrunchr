package au.com.numbrcrunchr.domain;

import java.util.Date;

public class PartialPeriodDetails {
	private final double proRataFinancialYear;
	private final Date financialYearEnd;

	public PartialPeriodDetails(double proRataFinancialYear,
			Date financialYearEnd) {
		super();
		this.proRataFinancialYear = proRataFinancialYear;
		this.financialYearEnd = financialYearEnd;
	}

	public double getProRataFinancialYear() {
		return proRataFinancialYear;
	}

	public Date getFinancialYearEnd() {
		return financialYearEnd;
	}
}
