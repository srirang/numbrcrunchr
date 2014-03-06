package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public abstract class TaxCalculatorTestCase {

    public abstract TaxCalculator getTaxCalculator();

    private static final Date referenceDate = new DateMidnight(2010, 8, 11)
            .toDate();

    @Test
    public void taxFor1Million() {
        assertEquals(438550,
                getTaxCalculator().calculateTax(referenceDate, 1000000, true),
                0);
        assertEquals(423550,
                getTaxCalculator().calculateTax(referenceDate, 1000000, false),
                0);
    }

    @Test
    public void taxFor102k() {
        assertEquals(27220,
                getTaxCalculator().calculateTax(referenceDate, 102000, true), 0);
        assertEquals(25690,
                getTaxCalculator().calculateTax(referenceDate, 102000, false),
                0);
    }

    @Test
    public void checkTaxOn118300() {
        assertEquals(33495,
                getTaxCalculator().calculateTax(referenceDate, 118300, true), 0);
    }

    @Test
    public void checkTaxOn125k() {
        double income = 125000;
        double nettIncome = getTaxCalculator().calculateNettIncome(
                referenceDate, income, true);
        assertEquals(7410.416666666667, nettIncome / 12,
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
    }

    @Test
    public void checkAgainstATOWebsite() {
        Date referenceDate = new DateMidnight(2013, 3, 5).toDate();
        assertEquals(7867,
                getTaxCalculator().calculateTax(referenceDate, 48000, true),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(
                40133,
                getTaxCalculator().calculateNettIncome(referenceDate, 48000,
                        true), MathUtilTest.ROUNDING_ERROR_TOLERANCE);
    }
}
