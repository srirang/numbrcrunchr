package com.numbrcrunchr.domain;

import com.numbrcrunchr.domain.TaxRateRepository;
import com.numbrcrunchr.domain.TaxRateRepositoryMapImpl;

public class TaxRateRepositoryMapImplTest extends
        TaxRateRepositoryAbstractTestCase {
    @Override
    public TaxRateRepository getRepository() {
        return new TaxRateRepositoryMapImpl();
    }
}
