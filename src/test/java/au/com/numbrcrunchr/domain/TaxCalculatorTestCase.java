package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger
            .getLogger(TaxCalculatorTestCase.class.getName());

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
        long income = 125000;
        long nettIncome = getTaxCalculator().calculateNettIncome(referenceDate,
                income, true);
        LOGGER.log(Level.INFO, "Nett Income: {0}", nettIncome);
        assertEquals(7410, nettIncome / 12);
    }
}
