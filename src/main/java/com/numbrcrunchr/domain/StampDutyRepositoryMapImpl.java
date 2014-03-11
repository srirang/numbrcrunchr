package com.numbrcrunchr.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author AMIS005
 */
public class StampDutyRepositoryMapImpl implements StampDutyRepository {
    private static final long serialVersionUID = 1L;

    private static final Map<String, StampDutyRate[]> STAMP_DUTY_RATES;
    static {
        STAMP_DUTY_RATES = new HashMap<String, StampDutyRate[]>();
        STAMP_DUTY_RATES
                .put(State.NSW,
                        new StampDutyRate[] {
                                new StampDutyRate(State.NSW, 0l, 14000l, 0l,
                                        0.0125),
                                new StampDutyRate(State.NSW, 14001l, 30000l,
                                        175, 0.015),
                                new StampDutyRate(State.NSW, 30001l, 80000l,
                                        415l, 0.0175),
                                new StampDutyRate(State.NSW, 80001l, 300000l,
                                        1290l, 0.035),
                                new StampDutyRate(State.NSW, 300001l, 1000000l,
                                        8990l, 0.045),
                                new StampDutyRate(State.NSW, 1000001l,
                                        3000000l, 40490l, 0.055),
                                new StampDutyRate(State.NSW, 3000001l, null, 0,
                                        0.07), });

        STAMP_DUTY_RATES.put(State.VIC, new StampDutyRate[] {
                new StampDutyRate(State.VIC, 0l, 25000l, 0, 0.014),
                new StampDutyRate(State.VIC, 25001l, 130000l, 350l, 0.024),
                new StampDutyRate(State.VIC, 130001l, 960000l, 2870l, 0.06),
                new StampDutyRate(State.VIC, 960001l, null, 0, 0.055), });

        STAMP_DUTY_RATES.put(State.QLD, new StampDutyRate[] {
                new StampDutyRate(State.QLD, null, 5000l, 0, 0),
                new StampDutyRate(State.QLD, 5000l, 75000l, 0, 0.015),
                new StampDutyRate(State.QLD, 75001l, 540000l, 1050l, 0.035),
                new StampDutyRate(State.QLD, 540001l, 1000000l, 17325l, 0.045),
                new StampDutyRate(State.QLD, 1000001l, null, 38025, 0.0575), });

        STAMP_DUTY_RATES.put(State.SA, new StampDutyRate[] {
                new StampDutyRate(State.SA, null, 12000l, 0, 0.01),
                new StampDutyRate(State.SA, 12001l, 30000l, 120l, 0.02),
                new StampDutyRate(State.SA, 30001l, 50000l, 480l, 0.03),
                new StampDutyRate(State.SA, 50001l, 100000l, 1080l, 0.035),
                new StampDutyRate(State.SA, 100001l, 200000l, 2830l, 0.04),
                new StampDutyRate(State.SA, 200001l, 250000l, 6830l, 0.0425),
                new StampDutyRate(State.SA, 250001l, 300000l, 8955l, 0.0475),
                new StampDutyRate(State.SA, 300001l, 500000l, 11330l, 0.05),
                new StampDutyRate(State.SA, 500001l, null, 21330l, 0.055), });

        STAMP_DUTY_RATES.put(State.WA, new StampDutyRate[] {
                new StampDutyRate(State.WA, null, 120000l, 0, 0.019),
                new StampDutyRate(State.WA, 120001l, 150000l, 2280l, 0.0285),
                new StampDutyRate(State.WA, 150001l, 360000l, 3135l, 0.038),
                new StampDutyRate(State.WA, 360001l, 725000l, 11115l, 0.0475),
                new StampDutyRate(State.WA, 725001l, null, 28453l, 0.0515), });

        STAMP_DUTY_RATES.put(State.TAS, new StampDutyRate[] {
                new StampDutyRate(State.TAS, null, 3000l, 50, 0.00),
                new StampDutyRate(State.TAS, 3001l, 25000l, 50, 0.0175),
                new StampDutyRate(State.TAS, 25001l, 75000l, 435, 0.0225),
                new StampDutyRate(State.TAS, 75001l, 200000l, 1560, 0.035),
                new StampDutyRate(State.TAS, 200001l, 375000l, 5935, 0.0435),
                new StampDutyRate(State.TAS, 375001l, 725000l, 12935, 0.425),
                new StampDutyRate(State.WA, 725001l, null, 27810, 0.045), });

        STAMP_DUTY_RATES.put(State.ACT, new StampDutyRate[] {
                new StampDutyRate(State.ACT, null, 200000l, 0, 0.022),
                new StampDutyRate(State.ACT, 200001l, 300000l, 4400l, 0.037),
                new StampDutyRate(State.ACT, 300001l, 500000l, 8100l, 0.045),
                new StampDutyRate(State.ACT, 500001l, 750000l, 17100l, 0.05),
                new StampDutyRate(State.ACT, 750001l, 1000000l, 29600l, 0.065),
                new StampDutyRate(State.ACT, 1000001l, 1650000l, 45850l, 0.07),
                new StampDutyRate(State.ACT, 1650001l, null, 0, 0.055),

        });
    }

    @Override
    public StampDutyRate getRate(String state, long value) {
        String normalState = State.normalise(state);
        StampDutyRate[] stampDutyRates = STAMP_DUTY_RATES.get(normalState);
        if (stampDutyRates == null) {
            throw new DataException(
                    "Unable to find any stamp dtuy rates for state: "
                            + String.valueOf(normalState) + " for value "
                            + value);
        }
        for (StampDutyRate stampDutyRate : stampDutyRates) {
            if (stampDutyRate.isInRange(value)) {
                return stampDutyRate;
            }
        }
        throw new DataException(
                "Unable to find any stamp dtuy rates for state: "
                        + String.valueOf(normalState) + " for value " + value);
    }

    @Override
    public boolean hasAllData() {
        return true;
    }
}
