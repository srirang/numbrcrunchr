package au.com.numbrcrunchr.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author AMIS005
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class PersistenceTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void saveOwner() {
        Owner owner = new Owner();
        owner.setAnnualIncome(100000l);

        Property property = new Property();
        property.setAddress("FTG Road");
        property.addOwner(owner);
        property.setState(State.VIC);
        owner.addProperty(property);

        EntityManager entityManager = entityManagerFactory
                .createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(property);
        entityManager.getTransaction().commit();
    }
}
