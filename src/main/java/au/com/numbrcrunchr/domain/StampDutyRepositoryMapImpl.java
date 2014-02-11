package au.com.numbrcrunchr.domain;

import java.math.BigDecimal;
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
        STAMP_DUTY_RATES.put(State.NSW, new StampDutyRate[] {
                new StampDutyRate(State.NSW, new Long("0"), new Long("14000"),
                        BigDecimal.ZERO, new BigDecimal("0.0125")),
                new StampDutyRate(State.NSW, new Long("14001"), new Long(
                        "30000"), new BigDecimal("175"),
                        new BigDecimal("0.015")),
                new StampDutyRate(State.NSW, new Long("30001"), new Long(
                        "80000"), new BigDecimal("415"), new BigDecimal(
                        "0.0175")),
                new StampDutyRate(State.NSW, new Long("80001"), new Long(
                        "300000"), new BigDecimal("1290"), new BigDecimal(
                        "0.035")),
                new StampDutyRate(State.NSW, new Long("300001"), new Long(
                        "1000000"), new BigDecimal("8990"), new BigDecimal(
                        "0.045")),
                new StampDutyRate(State.NSW, new Long("1000001"), null,
                        new BigDecimal("40490"), new BigDecimal("0.055")), });

        STAMP_DUTY_RATES.put(State.VIC, new StampDutyRate[] {
                new StampDutyRate(State.VIC, new Long("0"), new Long("25000"),
                        BigDecimal.ZERO, new BigDecimal("0.014")),
                new StampDutyRate(State.VIC, new Long("25001"), new Long(
                        "130000"), new BigDecimal("350"), new BigDecimal(
                        "0.024")),
                new StampDutyRate(State.VIC, new Long("130001"), new Long(
                        "960000"), new BigDecimal("2870"), new BigDecimal(
                        "0.06")),
                new StampDutyRate(State.VIC, new Long("960001"), null,
                        BigDecimal.ZERO, new BigDecimal("0.055")), });

        STAMP_DUTY_RATES.put(State.QLD, new StampDutyRate[] {
                new StampDutyRate(State.QLD, null, new Long("5000"),
                        BigDecimal.ZERO, BigDecimal.ZERO),
                new StampDutyRate(State.QLD, new Long("5000"),
                        new Long("75000"), BigDecimal.ZERO, new BigDecimal(
                                "0.015")),
                new StampDutyRate(State.QLD, new Long("75001"), new Long(
                        "540000"), new BigDecimal("1050"), new BigDecimal(
                        "0.035")),
                new StampDutyRate(State.QLD, new Long("540001"), new Long(
                        "980000"), new BigDecimal("17325"), new BigDecimal(
                        "0.045")),
                new StampDutyRate(State.QLD, new Long("980001"), null,
                        new BigDecimal("37125"), new BigDecimal("0.0525")), });

        STAMP_DUTY_RATES.put(State.SA, new StampDutyRate[] {
                new StampDutyRate(State.SA, null, new Long("12000"),
                        BigDecimal.ZERO, new BigDecimal("0.01")),
                new StampDutyRate(State.SA, new Long("12001"),
                        new Long("30000"), new BigDecimal("120"),
                        new BigDecimal("0.02")),
                new StampDutyRate(State.SA, new Long("30001"),
                        new Long("50000"), new BigDecimal("480"),
                        new BigDecimal("0.03")),
                new StampDutyRate(State.SA, new Long("50001"), new Long(
                        "100000"), new BigDecimal("1080"), new BigDecimal(
                        "0.035")),
                new StampDutyRate(State.SA, new Long("100001"), new Long(
                        "200000"), new BigDecimal("2830"), new BigDecimal(
                        "0.04")),
                new StampDutyRate(State.SA, new Long("200001"), new Long(
                        "250000"), new BigDecimal("6830"), new BigDecimal(
                        "0.0425")),
                new StampDutyRate(State.SA, new Long("250001"), new Long(
                        "300000"), new BigDecimal("8955"), new BigDecimal(
                        "0.0475")),
                new StampDutyRate(State.SA, new Long("300001"), new Long(
                        "500000"), new BigDecimal("11330"), new BigDecimal(
                        "0.05")),
                new StampDutyRate(State.SA, new Long("500001"), null,
                        new BigDecimal("21330"), new BigDecimal("0.055")), });

        STAMP_DUTY_RATES.put(State.WA, new StampDutyRate[] {
                new StampDutyRate(State.WA, null, new Long("120000"),
                        BigDecimal.ZERO, new BigDecimal("0.019")),
                new StampDutyRate(State.WA, new Long("120001"), new Long(
                        "150000"), new BigDecimal("2280"), new BigDecimal(
                        "0.0285")),
                new StampDutyRate(State.WA, new Long("150001"), new Long(
                        "360000"), new BigDecimal("3135"), new BigDecimal(
                        "0.038")),
                new StampDutyRate(State.WA, new Long("360001"), new Long(
                        "725000"), new BigDecimal("11115"), new BigDecimal(
                        "0.0475")),
                new StampDutyRate(State.WA, new Long("725001"), null,
                        new BigDecimal("28453"), new BigDecimal("0.0515")), });

        STAMP_DUTY_RATES
                .put(State.TAS,
                        new StampDutyRate[] {
                                new StampDutyRate(State.TAS, null, new Long(
                                        "1300"), new BigDecimal("20"),
                                        new BigDecimal("0.00")),
                                new StampDutyRate(State.TAS, new Long("1301"),
                                        new Long("10000"), BigDecimal.ZERO,
                                        new BigDecimal("0.015")),
                                new StampDutyRate(State.TAS, new Long("10001"),
                                        new Long("30000"),
                                        new BigDecimal("150"), new BigDecimal(
                                                "0.02")),
                                new StampDutyRate(State.TAS, new Long("30001"),
                                        new Long("75000"),
                                        new BigDecimal("550"), new BigDecimal(
                                                "0.025")),
                                new StampDutyRate(State.TAS, new Long("75001"),
                                        new Long("150000"), new BigDecimal(
                                                "1675"), new BigDecimal("0.03")),
                                new StampDutyRate(State.TAS,
                                        new Long("150001"), new Long("225000"),
                                        new BigDecimal("3925"), new BigDecimal(
                                                "0.035")),
                                new StampDutyRate(State.WA, new Long("225001"),
                                        null, new BigDecimal("6550"),
                                        new BigDecimal("0.04")), });

        STAMP_DUTY_RATES.put(State.ACT, new StampDutyRate[] {
                new StampDutyRate(State.ACT, null, new Long("100000"),
                        BigDecimal.ZERO, new BigDecimal("0.02")),
                new StampDutyRate(State.ACT, new Long("100001"), new Long(
                        "200000"), new BigDecimal("2000"), new BigDecimal(
                        "0.03")),
                new StampDutyRate(State.ACT, new Long("200001"), new Long(
                        "300000"), new BigDecimal("5500"), new BigDecimal(
                        "0.04")),
                new StampDutyRate(State.ACT, new Long("300001"), new Long(
                        "500000"), new BigDecimal("9500"), new BigDecimal(
                        "0.055")),
                new StampDutyRate(State.ACT, new Long("500001"), new Long(
                        "1000000"), new BigDecimal("20500"), new BigDecimal(
                        "0.0575")),
                new StampDutyRate(State.ACT, new Long("1000000"), null,
                        new BigDecimal("49250"), new BigDecimal("0.0675")), });
    }

    public StampDutyRate getRate(String state, long value) {
        state = State.normalise(state);
        StampDutyRate[] stampDutyRates = STAMP_DUTY_RATES.get(state);
        if (stampDutyRates == null) {
            throw new DataException(
                    "Unable to find any stamp dtuy rates for state: "
                            + String.valueOf(state) + " for value " + value);
        }
        for (StampDutyRate stampDutyRate : stampDutyRates) {
            if (stampDutyRate.isInRange(value)) {
                return stampDutyRate;
            }
        }
        throw new DataException(
                "Unable to find any stamp dtuy rates for state: "
                        + String.valueOf(state) + " for value " + value);
    }

    @Override
    public boolean hasAllData() {
        return true;
    }
}
