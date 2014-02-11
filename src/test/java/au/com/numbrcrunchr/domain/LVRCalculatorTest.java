package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LVRCalculatorTest {
    @Test
    public void checkLvr() {
        assertEquals(50, new LVRCalculator().calculateLvr(100000l, 200000), 0);
    }

    @Test
    public void checkLoanAmount() {
        assertEquals(100000,
                new LVRCalculator().calculateLoanAmount(200000l, .5));
    }

    @Test
    public void checkLoanAmountFor0PropertyValue() {
        assertEquals(0, new LVRCalculator().calculateLoanAmount(0l, .5));
    }

    @Test
    public void checkLvrWith0Value() {
        assertEquals(0, new LVRCalculator().calculateLvr(100000l, 0), 0);
    }
}
