package au.com.numbrcrunchr.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.jpa.JpaCallback;

/**
 * 
 * @author AMIS005
 */
public class StampDutyRepositoryJpaImpl extends AbstractJpaRepository implements
        StampDutyRepository {

    private static final long serialVersionUID = 1L;

    @Override
    public StampDutyRate getRate(String state, long value) {
        if (state == null) {
            throw new DataException(
                    "Unable to find any stamp dtuy rates for state: null");
        }
        String normalState = State.normalise(state);
        List<StampDutyRate> stampDutyRates = getJpaTemplate()
                .findByNamedParams(
                        "SELECT r FROM StampDutyRate r where r.state = :state",
                        parameters("state", normalState));
        if (CollectionUtils.isEmpty(stampDutyRates)) {
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
        List<StampDutyRate> rates = getJpaTemplate().find(
                "SELECT r FROM StampDutyRate r");
        return rates.size() == 42;
    }

    public void deleteAllData() {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.getTransaction().begin();
                em.createQuery("delete from StampDutyRate").executeUpdate();
                em.getTransaction().commit();
                return null;
            }
        });
    }
}
