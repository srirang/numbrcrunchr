package com.numbrcrunchr.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * 
 * @author AMIS005
 */
public class PropertyRepositoryJpaImpl extends JpaDaoSupport implements
        PropertyRepository {

    public void saveProperty(final Property property) {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager)
                    throws PersistenceException {
                entityManager.getTransaction().begin();
                entityManager.persist(property);
                entityManager.getTransaction().commit();
                return property;
            }
        });
    }
}
