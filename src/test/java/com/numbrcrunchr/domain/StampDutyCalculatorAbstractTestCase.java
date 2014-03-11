package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class StampDutyCalculatorAbstractTestCase {

    abstract StampDutyCalculator getStampDutyCalculator();

    @Test
    public void calcualteStampDutyForVIC() {
        assertEquals(
                0,
                getStampDutyCalculator().calculateStampDuty(State.VIC,
                        new Long("0")), 0);
        assertEquals(
                2150,
                getStampDutyCalculator().calculateStampDuty(State.VIC,
                        new Long("100000")), 0);
    }

    @Test
    public void calcualteStampDutyForNT() {
        assertEquals(
                0,
                getStampDutyCalculator().calculateStampDuty(State.NT,
                        new Long("0")), 0);
        assertEquals(
                5628.5764,
                getStampDutyCalculator().calculateStampDuty(State.NT,
                        new Long("200000")), 0);
        assertEquals(
                34650,
                getStampDutyCalculator().calculateStampDuty(State.NT,
                        new Long("700000")), 0);
    }

    @Test
    public void specialCaseForACT() {
        // TODO: Stamp duty for ACT shows as $20 for $0 purchase price on UI!
        assertEquals(
                0,
                getStampDutyCalculator().calculateStampDuty(State.ACT,
                        new Long("0")), 0);
        assertEquals(
                2.706,
                getStampDutyCalculator().calculateStampDuty(State.ACT,
                        new Long("123")), 0);
        assertEquals(
                2200,
                getStampDutyCalculator().calculateStampDuty(State.ACT,
                        new Long("100000")), 0);
        assertEquals(
                90750.055,
                getStampDutyCalculator().calculateStampDuty(State.ACT,
                        new Long("1650001")), 0);
        assertEquals(
                275000,
                getStampDutyCalculator().calculateStampDuty(State.ACT,
                        new Long("5000000")), 0);
        assertEquals(
                17100,
                getStampDutyCalculator().calculateStampDuty(State.ACT, 500000l),
                0);
    }

    @Test
    public void calcualteStampDutyForNSW() {
        assertEquals(35000,
                getStampDutyCalculator()
                        .calculateStampDuty(State.NSW, 3500000l), 0);
    }

    @Test
    public void calcualteStampDutyForTAS() {
        assertEquals(
                10285,
                getStampDutyCalculator().calculateStampDuty(State.TAS, 300000l),
                0);
    }
}
