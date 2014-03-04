package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OwnerTest {

    @Test
    public void checkEquals() {
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        assertFalse(owner1.equals(null));
        assertFalse(owner1.equals("test"));
        assertTrue(owner1.equals(owner2));
        assertTrue(owner2.equals(owner1));
        owner2.setIdOwner(2);
        assertFalse(owner1.equals(owner2));
        assertFalse(owner2.equals(owner1));

        owner2.setIdOwner(null);
        assertTrue(owner1.equals(owner2));
        assertTrue(owner2.equals(owner1));

        owner1.setIdOwner(null);
        assertTrue(owner1.equals(owner2));
        assertTrue(owner2.equals(owner1));
    }

}
