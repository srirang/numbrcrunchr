package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author AMIS005
 */
public class StampDutyCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    private StampDutyRepository stampDutyRepository;

    public Long calculateStampDuty(String state, Long value) {
        // Special case #1: NT has unique rules for stamp duty
        if (State.NT.equals(State.normalise(state))) {
            if (value.compareTo(new Long(525000)) > 0) {
                return MathUtil.doubleToLong(0.0495 * value.longValue());
            } else {
                long v = value / 1000;
                double stampDuty = (0.06571441 * v * v) + (15 * v);
                return MathUtil.doubleToLong(stampDuty);
            }
        }
        // Special case #2: ACT has unique rules for stamp duty for <= $100,000
        if (value.compareTo(new Long("0")) > 0 && State.ACT.equals(state)
                && value.compareTo(new Long(100000)) <= 0) {
            return MathUtil.doubleToLong(Math.max(20, (value * 0.02)));
        }

        // All other states
        StampDutyRate rate = stampDutyRepository.getRate(state, value);
        Long stampDuty = MathUtil.bigDecimalToLong(rate.getFlatRate()
                .add(new BigDecimal(value.longValue()).multiply(rate
                        .getPercentage())));
        return stampDuty;
    }

    public void setStampDutyRepository(StampDutyRepository stampDutyRepository) {
        this.stampDutyRepository = stampDutyRepository;
    }

    public StampDutyCalculator(StampDutyRepository stampDutyRepository) {
        this.stampDutyRepository = stampDutyRepository;
    }

    public StampDutyCalculator() {
    }

}
