package au.com.numbrcrunchr;

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

import au.com.numbrcrunchr.domain.DataException;
import au.com.numbrcrunchr.domain.StampDutyRate;
import au.com.numbrcrunchr.domain.StampDutyRepository;
import au.com.numbrcrunchr.domain.StampDutyRepositoryJpaImpl;
import au.com.numbrcrunchr.domain.TaxRate;
import au.com.numbrcrunchr.domain.TaxRateRepository;
import au.com.numbrcrunchr.domain.TaxRateRepositoryJpaImpl;

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
        assertEquals(64, sqls.size());
    }

    @Test
    public void checkInitialiseDb() {
        dbInitialiser.initialiseDb("schema/insert.sql");
    }

    @Test
    public void needsUpdating() {
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void clearDbAndInitialise() {
        // Delete reference data
        delete(TaxRate.class.getSimpleName());
        delete(StampDutyRate.class.getSimpleName());
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(stampDutyRepository.hasAllData());
        assertFalse(taxRateRepository.hasAllData());

        dbInitialiser.initialiseDb("schema/insert.sql");
        assertTrue(stampDutyRepository.hasAllData());
        assertTrue(taxRateRepository.hasAllData());
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void clearSomeReferenceDataFromDbAndInitialise() {
        // Delete reference data
        delete(TaxRate.class.getSimpleName());
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(stampDutyRepository.hasAllData());
        assertFalse(taxRateRepository.hasAllData());

        dbInitialiser.initialiseDb("schema/insert.sql");
        assertTrue(stampDutyRepository.hasAllData());
        assertTrue(taxRateRepository.hasAllData());
        assertFalse(dbInitialiser.needsUpdating());
    }

    @Test
    public void clearSomeDbAndInitialiseUsingPostProcessing() {
        dbInitialiser.postProcessBeanFactory(null);
        delete(StampDutyRate.class.getSimpleName());
        // Delete reference data
        assertTrue(dbInitialiser.needsUpdating());
        assertFalse(stampDutyRepository.hasAllData());
        assertTrue(dbInitialiser.needsUpdating());

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

    private void delete(final String className) {
        // new JpaTemplate(entityManagerFactory)
        // .execute(new JpaCallback<Object>() {
        // @Override
        // public Object doInJpa(EntityManager em)
        // throws PersistenceException {
        // em.getTransaction().begin();
        // em.createQuery("delete from " + className + " r")
        // .executeUpdate();
        // em.getTransaction().commit();
        // return null;
        // }
        // });
        // Connection connection = ((SessionImpl) entityManagerFactory
        // .createEntityManager().getDelegate()).getJDBCContext()
        // .getConnectionManager().getConnection();
        // try {
        // JdbcTemplate jdbcTemplate = new JdbcTemplate(
        // new SingleConnectionDataSource(connection, true));
        // jdbcTemplate.update("DELETE FROM Stamp_Duty_Rates");
        // jdbcTemplate.update("DELETE FROM Payg_Tax_Rates");
        // connection.commit();
        // } catch (DataAccessException e) {
        // try {
        // connection.close();
        // } catch (SQLException e1) {
        // LOGGER.severe("Error initialising database!" + e1 + e);
        // }
        // } catch (SQLException e) {
        // try {
        // connection.close();
        // } catch (SQLException e1) {
        // LOGGER.severe("Error initialising database!" + e1 + e);
        // }
        // }
        ((StampDutyRepositoryJpaImpl) stampDutyRepository).deleteAllData();
        ((TaxRateRepositoryJpaImpl) taxRateRepository).deleteAllData();
    }

}
