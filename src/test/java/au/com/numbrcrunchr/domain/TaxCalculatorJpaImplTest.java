package au.com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class TaxCalculatorJpaImplTest extends TaxCalculatorTestCase {
	@Autowired
	private TaxCalculator taxCalculator;

	@Override
	public TaxCalculator getTaxCalculator() {
		return taxCalculator;
	}
}
