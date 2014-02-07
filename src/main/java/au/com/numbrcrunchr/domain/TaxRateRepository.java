package au.com.numbrcrunchr.domain;

public interface TaxRateRepository {
	TaxRate getRate(String taxYear, long income);

	boolean hasAllData();
}
