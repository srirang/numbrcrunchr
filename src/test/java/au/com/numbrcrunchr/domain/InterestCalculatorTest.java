package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InterestCalculatorTest {
    @Test
    public void checkInterestCalculator() {
        InterestCalculator calculator = new InterestCalculator();
        assertEquals(7000, calculator.calculateInterest(100000, 7), 0);
    }
}
