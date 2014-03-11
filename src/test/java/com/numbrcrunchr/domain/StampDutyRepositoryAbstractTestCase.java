package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public abstract class StampDutyRepositoryAbstractTestCase {

    public abstract StampDutyRepository getStampDutyRepository();

    @Test
    public void findRateForVic() {
        StampDutyRate rate = getStampDutyRepository().getRate("vic",
                new Long("0"));
        assertEquals(0, rate.getFlatRate());
        assertEquals(0.014, rate.getPercentage(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(new Long("25000"), rate.getUpperLimit());
        assertEquals(0, rate.getLowerLimit().doubleValue(), 0);
        assertEquals(State.VIC, rate.getState());
    }

    @Test
    public void findAllRatesForVic() {
        assertEquals(0.014,
                getStampDutyRepository().getRate("vic", new Long("0"))
                        .getPercentage(), 0);
        assertEquals(0.014,
                getStampDutyRepository().getRate("vic", new Long("20000"))
                        .getPercentage(), 0);
        assertEquals(0.0, getStampDutyRepository()
                .getRate("vic", new Long("0")).getFlatRate(), 0);
        assertEquals(0.0,
                getStampDutyRepository().getRate("vic", new Long("20000"))
                        .getFlatRate(), 0);

        assertEquals(0.014,
                getStampDutyRepository().getRate("vic", new Long("20001"))
                        .getPercentage(), 0);
        assertEquals(0.024,
                getStampDutyRepository().getRate("vic", new Long("115000"))
                        .getPercentage(), 0);
        assertEquals(0,
                getStampDutyRepository().getRate("vic", new Long("20001"))
                        .getFlatRate(), 0);
        assertEquals(350.0,
                getStampDutyRepository().getRate("vic", new Long("115000"))
                        .getFlatRate(), 0);

        assertEquals(0.024,
                getStampDutyRepository().getRate("vic", new Long("115001"))
                        .getPercentage(), 0);
        assertEquals(0.06,
                getStampDutyRepository().getRate("vic", new Long("870000"))
                        .getPercentage(), 0);
        assertEquals(350.0,
                getStampDutyRepository().getRate("vic", new Long("115001"))
                        .getFlatRate(), 0);
        assertEquals(2870.0,
                getStampDutyRepository().getRate("vic", new Long("870000"))
                        .getFlatRate(), 0);

        assertEquals(0.06,
                getStampDutyRepository().getRate("vic", new Long("870001"))
                        .getPercentage(), 0);
        assertEquals(0.055,
                getStampDutyRepository().getRate("vic", new Long("1000000"))
                        .getPercentage(), 0);
        assertEquals(2870,
                getStampDutyRepository().getRate("vic", new Long("870001"))
                        .getFlatRate(), 0);
        assertEquals(0.0,
                getStampDutyRepository().getRate("vic", new Long("1000000"))
                        .getFlatRate(), 0);
    }

    @Test
    public void findAllRatesForQld() {
        assertEquals(0, getStampDutyRepository().getRate("qld", new Long("0"))
                .getPercentage(), 0);
        assertEquals(0.015,
                getStampDutyRepository().getRate("qld", new Long("20000"))
                        .getPercentage(), 0);
        assertEquals(0.0, getStampDutyRepository()
                .getRate("qld", new Long("0")).getFlatRate(), 0);
        assertEquals(0.0,
                getStampDutyRepository().getRate("qld", new Long("20000"))
                        .getFlatRate(), 0);

        assertEquals(0.015,
                getStampDutyRepository().getRate("qld", new Long("20001"))
                        .getPercentage(), 0);
        assertEquals(0.015,
                getStampDutyRepository().getRate("qld", new Long("50000"))
                        .getPercentage(), 0);
        assertEquals(0,
                getStampDutyRepository().getRate("qld", new Long("20001"))
                        .getFlatRate(), 0);
        assertEquals(0,
                getStampDutyRepository().getRate("qld", new Long("50000"))
                        .getFlatRate(), 0);

        assertEquals(0.015,
                getStampDutyRepository().getRate("qld", new Long("50001"))
                        .getPercentage(), 0);
        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("100000"))
                        .getPercentage(), 0);
        assertEquals(0,
                getStampDutyRepository().getRate("qld", new Long("50001"))
                        .getFlatRate(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("100000"))
                        .getFlatRate(), 0);

        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("100001"))
                        .getPercentage(), 0);
        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("250000"))
                        .getPercentage(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("100001"))
                        .getFlatRate(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("250000"))
                        .getFlatRate(), 0);

        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("250001"))
                        .getPercentage(), 0);
        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("500000"))
                        .getPercentage(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("250001"))
                        .getFlatRate(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("500000"))
                        .getFlatRate(), 0);

        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("250001"))
                        .getPercentage(), 0);
        assertEquals(0.035,
                getStampDutyRepository().getRate("qld", new Long("500000"))
                        .getPercentage(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("250001"))
                        .getFlatRate(), 0);
        assertEquals(1050,
                getStampDutyRepository().getRate("qld", new Long("500000"))
                        .getFlatRate(), 0);

        assertEquals(0.045,
                getStampDutyRepository().getRate("qld", new Long("1000000"))
                        .getPercentage(), 0);
        assertEquals(38025,
                getStampDutyRepository().getRate("qld", new Long("20250001"))
                        .getFlatRate(), 0);
    }

    @Test
    public void checkForNulls() {
        try {
            getStampDutyRepository().getRate(null, 0);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected
        }
        try {
            getStampDutyRepository().getRate(null, 100000l);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected
        }
    }

    @Test
    public void checkForNonExistingValue() {
        try {
            getStampDutyRepository().getRate("abcdstate", 1232l);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected
        }
    }
}
