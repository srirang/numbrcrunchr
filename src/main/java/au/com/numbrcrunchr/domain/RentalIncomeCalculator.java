package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public class RentalIncomeCalculator implements Serializable {
	private static final long serialVersionUID = 1L;

	public Long calculateAnnualFee(Long weeklyRent, double commission,
			int numberOfWeeksRented) {
		return MathUtil.doubleToLong(calculateGrossAnnualRent(
				weeklyRent, numberOfWeeksRented)
				* commission / 100);
	}

	public long calculateGrossAnnualRent(Long weeklyRent,
			int numberOfWeeksRented) {
		return weeklyRent * numberOfWeeksRented;
	}
}
