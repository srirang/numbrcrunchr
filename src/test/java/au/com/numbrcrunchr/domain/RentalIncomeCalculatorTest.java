package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RentalIncomeCalculatorTest {

    @Test
    public void checkSimpleFee() {
        assertEquals(32 * 52,
                new RentalIncomeCalculator().calculateAnnualFee(320l, 10, 52), 0);
    }

    @Test
    public void checkRent() {
        assertEquals(16640,
                new RentalIncomeCalculator().calculateGrossAnnualRent(320l,
                52), 0);
    }
}
