package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaxRateTest {
    @Test
    public void checkCompare() {
        TaxRate rate1 = new TaxRate(0, 100, 0, 0.10);
        TaxRate rate2 = new TaxRate(0, 100, 0, 0.10);
        assertEquals(rate1, rate2);
    }
}
