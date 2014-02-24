package au.com.numbrcrunchr.domain;

import java.util.List;

public class TaxRateRepositoryJpaImpl extends AbstractJpaRepository implements
        TaxRateRepository {
    public static final String TAX_YEAR_2010_2011 = "2010-2011";

    public TaxRateRepositoryJpaImpl() {
    }

    @Override
    public TaxRate getRate(String taxYear, double income) {
        if (taxYear == null) {
            throw new DataException(
                    "Unable to find any tax rates for tax year: null");
        }
        taxYear = State.normalise(taxYear);
        List<TaxRate> taxRates = getJpaTemplate().findByNamedParams(
                "SELECT r FROM TaxRate r where r.taxYear = :taxYear",
                parameters("taxYear", taxYear));
        if (taxRates == null || taxRates.isEmpty()) {
            throw new DataException("Unable to find any tax rates for : "
                    + String.valueOf(taxYear) + " for income " + income);
        }
        for (TaxRate TaxRate : taxRates) {
            if (TaxRate.isInRange(MathUtil.doubleToLong(income))) {
                return TaxRate;
            }
        }
        throw new DataException("Unable to find any tax rates for : "
                + String.valueOf(taxYear) + " for income " + income);
    }

    @Override
    public boolean hasAllData() {
        List<TaxRate> rates = getJpaTemplate().find("SELECT r FROM TaxRate r");
        return rates.size() == 20;
    }
}
