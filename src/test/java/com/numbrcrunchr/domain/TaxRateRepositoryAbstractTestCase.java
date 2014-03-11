package com.numbrcrunchr.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.domain.DataException;
import com.numbrcrunchr.domain.TaxRate;
import com.numbrcrunchr.domain.TaxRateRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public abstract class TaxRateRepositoryAbstractTestCase {
    public abstract TaxRateRepository getRepository();

    @Test
    public void findSomeRates() {
        TaxRate rate = getRepository().getRate("2010-2011", 102000);
        assertNotNull(rate);
    }

    @Test
    public void findForNull() {
        try {
            getRepository().getRate(null, 0);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected;
        }
    }

    @Test
    public void findNonExistingRate() {
        try {
            getRepository().getRate("2010-2011", -12312);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected;
        }
    }

    @Test
    public void findInvalid() {
        try {
            getRepository().getRate("abcd", 1000);
            fail("DataException Expected");
        } catch (DataException e) {
            // Expected;
        }
    }
}
