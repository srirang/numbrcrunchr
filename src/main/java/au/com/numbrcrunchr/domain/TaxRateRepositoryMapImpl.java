package au.com.numbrcrunchr.domain;

import java.util.HashMap;
import java.util.Map;

public class TaxRateRepositoryMapImpl implements TaxRateRepository {
    private static final Map<String, TaxRate[]> TAX_RATES;

    private static final String TAX_YEAR_2010_2011 = "2010-2011";
    private static final String TAX_YEAR_2011_2012 = "2011-2012";

    static {
        TAX_RATES = new HashMap<String, TaxRate[]>();
        TAX_RATES.put(TAX_YEAR_2010_2011, new TaxRate[] {
                new TaxRate(0l, 6000l, 0, 0),
                new TaxRate(6001l, 37000l, 0, .15),
                new TaxRate(37001l, 80000l, 4650, 0.30),
                new TaxRate(80001l, 180000l, 17550, 0.37),
                new TaxRate(180001l, null, 54550l, 0.45), });
        TAX_RATES.put(TAX_YEAR_2011_2012, new TaxRate[] {
                new TaxRate(0l, 6000l, 0, 0),
                new TaxRate(6001l, 37000l, 0, .15),
                new TaxRate(37001l, 80000l, 4650, 0.30),
                new TaxRate(80001l, 180000l, 17550, 0.37),
                new TaxRate(180001l, null, 54550, 0.45), });
    }

    @Override
    public TaxRate getRate(String taxYear, double income) {
        if (taxYear == null) {
            throw new DataException(
                    "Unable to find any tax rates for tax year: null");
        }
        taxYear = State.normalise(taxYear);
        TaxRate[] taxRates = TAX_RATES.get(taxYear);
        if (taxRates == null) {
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
        return true;
    }
}
