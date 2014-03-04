package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StampDutyRateTest {
    @Test
    public void lowerLimitIsNull() {
        StampDutyRate rate = new StampDutyRate("VIC", null, 20000l, 0, 0.014);
        assertTrue(rate.isInRange(0l));
        assertTrue(rate.isInRange(100l));
        assertTrue(rate.isInRange(20000l));

        assertFalse(rate.isInRange(20001l));
        assertFalse(rate.isInRange(100000l));
    }

    @Test
    public void upperLimitIsNull() {
        StampDutyRate rate = new StampDutyRate("NSW", 1000001l, null, 40490,
                0.055);
        assertTrue(rate.isInRange(1000001l));
        assertTrue(rate.isInRange(10000011213l));

        assertFalse(rate.isInRange(0l));
        assertFalse(rate.isInRange(100l));
        assertFalse(rate.isInRange(20000l));
        assertFalse(rate.isInRange(1000000l));
    }

    @Test
    public void checkCompare() {
        StampDutyRate rate1 = new StampDutyRate("NSW", 1000001l, null, 40490,
                0.055);
        StampDutyRate rate2 = new StampDutyRate("NSW", 1000001l, null, 40490,
                0.055);
        assertEquals(rate1, rate2);

        rate1 = new StampDutyRate("NSW", 1000001l, null, 40490l, 0.055);
        rate2 = new StampDutyRate("VIC", 1000001l, null, 40490l, 0.055);
        assertNotSame(rate1, rate2);

    }

    @Test
    public void checkEquals() {
        StampDutyRate rate1 = new StampDutyRate("NSW", 1000001l, null, 40490,
                0.055);
        StampDutyRate rate2 = new StampDutyRate("NSW", 1000001l, null, 40490,
                0.055);
        assertFalse(rate1.equals(null));
        assertFalse(rate1.equals("test"));
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));
        assertEquals("NSW\t1000001 < null = 40490 + 0.055", rate1.toString());
        rate2.setId(2);
        assertFalse(rate1.equals(rate2));
        assertFalse(rate2.equals(rate1));

        rate2.setId(null);
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));

        rate1.setId(null);
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));
    }
}
