package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StampDutyCalculatorTest {

    private static StampDutyCalculator calculator = new StampDutyCalculator(
            new StampDutyRepositoryMapImpl());

    @Test
    public void calcualteStampDutyForVIC() {
        assertEquals(0, calculator.calculateStampDuty(State.VIC, new Long("0"))
                .doubleValue(), 0);
        assertEquals(2150,
                calculator.calculateStampDuty(State.VIC, new Long("100000"))
                        .doubleValue(), 0);
    }

    @Test
    public void calcualteStampDutyForNT() {
        assertEquals(0, calculator.calculateStampDuty(State.NT, new Long("0"))
                .doubleValue(), 0);
        assertEquals(5629,
                calculator.calculateStampDuty(State.NT, new Long("200000"))
                        .doubleValue(), 0);
        assertEquals(34650,
                calculator.calculateStampDuty(State.NT, new Long("700000"))
                        .doubleValue(), 0);
    }

    @Test
    public void specialCaseForACT() {
        assertEquals(0, calculator.calculateStampDuty(State.ACT, new Long("0"))
                .doubleValue(), 0);
        assertEquals(20,
                calculator.calculateStampDuty(State.ACT, new Long("123"))
                        .doubleValue(), 0);
        assertEquals(2000,
                calculator.calculateStampDuty(State.ACT, new Long("100000"))
                        .doubleValue(), 0);
    }
}
