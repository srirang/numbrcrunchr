package com.numbrcrunchr.domain;

import com.numbrcrunchr.domain.TaxCalculator;
import com.numbrcrunchr.domain.TaxRateRepositoryMapImpl;

public class TaxCalculatorMapImplTest extends TaxCalculatorTestCase {
    @Override
    public TaxCalculator getTaxCalculator() {
        TaxCalculator calculator = new TaxCalculator();
        calculator.setTaxRateRepository(new TaxRateRepositoryMapImpl());
        return calculator;
    }
}
