package com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;

public class TaxRateRepositoryJpaImpl extends AbstractJpaRepository implements
        TaxRateRepository, Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TAX_YEAR_2010_2011 = "2010-2011";

    @Override
    public TaxRate getRate(String taxYear, double income) {
        if (taxYear == null) {
            throw new DataException(
                    "Unable to find any tax rates for tax year: null");
        }
        String normalTaxYear = State.normalise(taxYear);
        List<TaxRate> taxRates = getJpaTemplate().findByNamedParams(
                "SELECT r FROM TaxRate r where r.taxYear = :taxYear",
                parameters("taxYear", normalTaxYear));
        if (taxRates == null || taxRates.isEmpty()) {
            throw new DataException("Unable to find any tax rates for : "
                    + String.valueOf(normalTaxYear) + " for income " + income);
        }
        for (TaxRate TaxRate : taxRates) {
            if (TaxRate.isInRange(MathUtil.doubleToLong(income))) {
                return TaxRate;
            }
        }
        throw new DataException("Unable to find any tax rates for : "
                + String.valueOf(normalTaxYear) + " for income " + income);
    }

    @Override
    public boolean hasAllData() {
        List<TaxRate> rates = getJpaTemplate().find("SELECT r FROM TaxRate r");
        return rates.size() == 20;
    }

    public void deleteAllData() {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                em.getTransaction().begin();
                em.createQuery("delete from TaxRate").executeUpdate();
                em.getTransaction().commit();
                return null;
            }
        });
    }
}
