package au.com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class TaxRateRepositoryJpaImplTest extends
		TaxRateRepositoryAbstractTestCase {
	@Autowired
	private TaxRateRepositoryJpaImpl taxRateRepositoryJpaImpl;

	public TaxRateRepository getRepository() {
		return taxRateRepositoryJpaImpl;
	}
}
