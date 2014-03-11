package com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.numbrcrunchr.domain.TaxCalculator;

public class TaxCalculatorJpaImplTest extends TaxCalculatorTestCase {
    @Autowired
    private TaxCalculator taxCalculator;

    @Override
    public TaxCalculator getTaxCalculator() {
        return taxCalculator;
    }
}
