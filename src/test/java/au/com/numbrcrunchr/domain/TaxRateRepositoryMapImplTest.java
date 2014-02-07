package au.com.numbrcrunchr.domain;



public class TaxRateRepositoryMapImplTest extends TaxRateRepositoryAbstractTestCase {
	@Override
	public TaxRateRepository getRepository() {
		return new TaxRateRepositoryMapImpl();
	}
}
