package au.com.numbrcrunchr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.numbrcrunchr.DbInitialiser;
import au.com.numbrcrunchr.domain.DataException;
import au.com.numbrcrunchr.domain.StampDutyRate;
import au.com.numbrcrunchr.domain.StampDutyRepository;
import au.com.numbrcrunchr.domain.TaxRate;
import au.com.numbrcrunchr.domain.TaxRateRepository;

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
		assertTrue(stampDutyRepository.hasAllData());
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
		assertTrue(taxRateRepository.hasAllData());

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
		new JpaTemplate(entityManagerFactory)
				.execute(new JpaCallback<Object>() {
					@Override
					public Object doInJpa(EntityManager em)
							throws PersistenceException {
						em.getTransaction().begin();
						em.createQuery("delete from " + className + " r")
								.executeUpdate();
						em.getTransaction().commit();
						return null;
					}
				});
	}

}
