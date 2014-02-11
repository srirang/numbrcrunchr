package au.com.numbrcrunchr.domain;

public class TaxCalculatorMapImplTest extends TaxCalculatorTestCase {
    @Override
    public TaxCalculator getTaxCalculator() {
        TaxCalculator calculator = new TaxCalculator();
        calculator.setTaxRateRepository(new TaxRateRepositoryMapImpl());
        return calculator;
    }
}
