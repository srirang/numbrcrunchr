package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class StampDutyRateTest {
    @Test
    public void lowerLimitIsNull() {
        StampDutyRate rate = new StampDutyRate("VIC", null, new Long("20000"),
                new BigDecimal("0"), new BigDecimal("0.014"));
        assertTrue(rate.isInRange(new Long("0")));
        assertTrue(rate.isInRange(new Long("100")));
        assertTrue(rate.isInRange(new Long("20000")));

        assertFalse(rate.isInRange(new Long("20001")));
        assertFalse(rate.isInRange(new Long("100000")));
    }

    @Test
    public void upperLimitIsNull() {
        StampDutyRate rate = new StampDutyRate("NSW", new Long("1000001"),
                null, new BigDecimal("40490"), new BigDecimal("0.055"));
        assertTrue(rate.isInRange(new Long("1000001")));
        assertTrue(rate.isInRange(new Long("10000011213")));

        assertFalse(rate.isInRange(new Long("0")));
        assertFalse(rate.isInRange(new Long("100")));
        assertFalse(rate.isInRange(new Long("20000")));
        assertFalse(rate.isInRange(new Long("1000000")));
    }

    @Test
    public void checkCompare() {
        StampDutyRate rate1 = new StampDutyRate("NSW", new Long("1000001"),
                null, new BigDecimal("40490"), new BigDecimal("0.055"));
        StampDutyRate rate2 = new StampDutyRate("NSW", new Long("1000001"),
                null, new BigDecimal("40490"), new BigDecimal("0.055"));
        assertEquals(rate1, rate2);

        rate1 = new StampDutyRate("NSW", new Long("1000001"), null,
                new BigDecimal("40490"), new BigDecimal("0.055"));
        rate2 = new StampDutyRate("VIC", new Long("1000001"), null,
                new BigDecimal("40490"), new BigDecimal("0.055"));
        assertNotSame(rate1, rate2);

    }
}
