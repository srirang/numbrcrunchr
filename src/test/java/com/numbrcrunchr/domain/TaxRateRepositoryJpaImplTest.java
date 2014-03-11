package com.numbrcrunchr.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.numbrcrunchr.domain.TaxRateRepository;
import com.numbrcrunchr.domain.TaxRateRepositoryJpaImpl;

public class TaxRateRepositoryJpaImplTest extends
        TaxRateRepositoryAbstractTestCase {
    @Autowired
    private TaxRateRepositoryJpaImpl taxRateRepositoryJpaImpl;

    public TaxRateRepository getRepository() {
        return taxRateRepositoryJpaImpl;
    }
}
