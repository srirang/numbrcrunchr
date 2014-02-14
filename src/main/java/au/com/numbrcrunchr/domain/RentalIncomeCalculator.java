package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public class RentalIncomeCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public double calculateAnnualFee(double weeklyRent, double commission,
            int numberOfWeeksRented) {
        return calculateGrossAnnualRent(weeklyRent, numberOfWeeksRented)
                * commission / 100;
    }

    public double calculateGrossAnnualRent(double weeklyRent,
            int numberOfWeeksRented) {
        return weeklyRent * numberOfWeeksRented;
    }
}
