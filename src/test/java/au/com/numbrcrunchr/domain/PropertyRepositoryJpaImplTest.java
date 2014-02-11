package au.com.numbrcrunchr.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class PropertyRepositoryJpaImplTest {
    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void checkSaveAProperty() {
        Owner owner = new Owner();
        owner.setAnnualIncome(100000l);

        Property property = new Property();
        property.setAddress("FTG Road");
        property.addOwner(owner);
        property.setState(State.VIC);
        owner.addProperty(property);

        propertyRepository.saveProperty(property);
    }
}
