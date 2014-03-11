package com.numbrcrunchr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.domain.DataException;
import com.numbrcrunchr.domain.StampDutyRepository;
import com.numbrcrunchr.domain.StampDutyRepositoryJpaImpl;
import com.numbrcrunchr.domain.TaxRateRepository;
import com.numbrcrunchr.domain.TaxRateRepositoryJpaImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class DbInitialiserTest {

    @Autowired
    private DbInitialiser dbInitialiser;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private StampDutyRepository stampDutyRepository;

    @Test
    public void checkFileContents() throws IOException {
        List<String> sqls = dbInitialiser.getSql("schema/insert.sql");
        assertNotNull(sqls);
        assertEquals(66, sqls.size());
    }

    @Test
    public void checkInitialiseDb() {
        dbInitialiser.postProcessBeanFactory(null);
    }

    @Test
    public void clearDbAndInitialise() {
        // Delete reference data
        deleteTaxRates();
        deleteStampDuties();
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(stampDutyRepository.hasAllData());
        assertFalse(taxRateRepository.hasAllData());

        dbInitialiser.postProcessBeanFactory(null);
        assertTrue(stampDutyRepository.hasAllData());
        assertTrue(taxRateRepository.hasAllData());
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void clearSomeReferenceDataFromDbAndInitialise() {
        // Delete reference data
        deleteTaxRates();
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(taxRateRepository.hasAllData());

        dbInitialiser.postProcessBeanFactory(null);
        assertTrue(stampDutyRepository.hasAllData());
        assertTrue(taxRateRepository.hasAllData());
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void clearSomeDbAndInitialiseUsingPostProcessing() {
        dbInitialiser.postProcessBeanFactory(null);
        deleteStampDuties();
        // Delete reference data
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(stampDutyRepository.hasAllData());

        dbInitialiser.postProcessBeanFactory(null);
        assertTrue(stampDutyRepository.hasAllData());
        assertTrue(taxRateRepository.hasAllData());
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void updateWhenFileDoesNotExist() {
        try {
            dbInitialiser.initialiseDb("asdf");
        } catch (DataException e) {
            // Expected;
        }
    }

    @Test
    public void checkCanShutdown() {
        dbInitialiser.shutdownDatabase();
    }

    private void deleteStampDuties() {
        ((StampDutyRepositoryJpaImpl) stampDutyRepository).deleteAllData();
    }

    private void deleteTaxRates() {
        ((TaxRateRepositoryJpaImpl) taxRateRepository).deleteAllData();
    }

}
