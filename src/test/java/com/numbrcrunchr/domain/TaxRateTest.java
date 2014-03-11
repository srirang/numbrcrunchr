package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.numbrcrunchr.domain.TaxRate;

public class TaxRateTest {
    @Test
    public void checkCompare() {
        TaxRate rate1 = new TaxRate(0l, 100l, 0, 0.10);
        TaxRate rate2 = new TaxRate(0l, 100l, 0, 0.10);
        assertEquals(rate1, rate2);
    }

    @Test
    public void checkEquals() {
        TaxRate rate1 = new TaxRate(0l, 100l, 0, 0.10);
        TaxRate rate2 = new TaxRate(0l, 100l, 0, 0.10);
        assertFalse(rate1.equals(null));
        assertFalse(rate1.equals("test"));
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));
        assertEquals("0 < 100 = 0 + 0.1", rate1.toString());
        rate2.setId(2);
        assertFalse(rate1.equals(rate2));
        assertFalse(rate2.equals(rate1));

        rate2.setId(null);
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));

        rate1.setId(null);
        assertTrue(rate1.equals(rate2));
        assertTrue(rate2.equals(rate1));

        rate1.setId(null);
        assertTrue(rate1.equals(rate2));
        rate1 = new TaxRate(0l, 100l, 0, 0.10);
        rate2.setId(null);
        assertTrue(rate1.equals(rate2));
        rate2 = new TaxRate(0l, 100l, 0, 0.10);

        rate1.setId(4);
        rate2.setId(45);
        assertFalse(rate1.equals(rate2));
    }
}
