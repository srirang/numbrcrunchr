package au.com.numbrcrunchr.domain;

import java.io.Serializable;

/**
 * 
 * @author AMIS005
 */
public class StampDutyCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    private StampDutyRepository stampDutyRepository;

    /**
     * Constructor for spring
     */
    public StampDutyCalculator() {

    }

    public double calculateStampDuty(String state, Double value) {
        return calculateStampDuty(state, value.longValue());
    }

    public double calculateStampDuty(String state, Long value) {
        // Special case #1: NT has unique rules for stamp duty
        if (State.NT.equals(State.normalise(state))) {
            if (value.compareTo(new Long(525000)) > 0) {
                return 0.0495 * value.doubleValue();
            } else {
                double v = value.doubleValue() / 1000;
                return 0.06571441 * v * v + 15 * v;
            }
        }
        // Special case #2: ACT has unique rules for stamp duty for <= $100,000
        if (value.compareTo(new Long("0")) > 0 && State.ACT.equals(state)
                && value.compareTo(new Long(100000)) <= 0) {
            return Math.max(20, value.doubleValue() * 0.02);
        }

        // All other states
        StampDutyRate rate = stampDutyRepository.getRate(state, value);
        double previousBand = rate.getLowerLimit() == null ? 0 : rate
                .getLowerLimit() - 1;
        if (previousBand < 0) {
            previousBand = 0;
        }
        double stampDuty = rate.getFlatRate()
                + (value.doubleValue() - previousBand) * rate.getPercentage();
        return stampDuty;
    }

    public void setStampDutyRepository(StampDutyRepository stampDutyRepository) {
        this.stampDutyRepository = stampDutyRepository;
    }

    public StampDutyCalculator(StampDutyRepository stampDutyRepository) {
        this.stampDutyRepository = stampDutyRepository;
    }
}
