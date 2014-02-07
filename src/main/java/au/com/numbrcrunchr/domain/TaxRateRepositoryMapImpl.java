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
				new TaxRate(0, 6000, 0, 0), new TaxRate(6001, 37000, 0, .15),
				new TaxRate(37001, 80000, 4650, 0.30),
				new TaxRate(80001, 180000, 17550, 0.37),
				new TaxRate(180001, null, 54550.0, 0.45), });
		TAX_RATES.put(TAX_YEAR_2011_2012, new TaxRate[] {
				new TaxRate(0, 6000, 0, 0), new TaxRate(6001, 37000, 0, .15),
				new TaxRate(37001, 80000, 4650, 0.30),
				new TaxRate(80001, 180000, 17550, 0.37),
				new TaxRate(180001, null, 54550.0, 0.45), });
	}

	public TaxRate getRate(String taxYear, long income) {
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
			if (TaxRate.isInRange(income)) {
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
