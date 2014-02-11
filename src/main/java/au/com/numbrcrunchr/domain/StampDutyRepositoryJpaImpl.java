package au.com.numbrcrunchr.domain;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * 
 * @author AMIS005
 */
public class StampDutyRepositoryJpaImpl extends AbstractJpaRepository implements
        StampDutyRepository {

    private static final long serialVersionUID = 1L;

    public StampDutyRepositoryJpaImpl() {
    }

    @SuppressWarnings("unchecked")
    public StampDutyRate getRate(String state, long value) {
        if (state == null) {
            throw new DataException(
                    "Unable to find any stamp dtuy rates for state: null");
        }
        state = State.normalise(state);
        List<StampDutyRate> stampDutyRates = getJpaTemplate()
                .findByNamedParams(
                        "SELECT r FROM StampDutyRate r where r.state = :state",
                        parameters("state", state));
        if (CollectionUtils.isEmpty(stampDutyRates)) {
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
        @SuppressWarnings("unchecked")
        List<StampDutyRate> rates = getJpaTemplate().find(
                "SELECT r FROM StampDutyRate r");
        return rates.size() == 42;
    }
}
