package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StateTest {
    @Test
    public void checkNormalise() {
        assertEquals("VIC", State.normalise("  vic  "));
        assertEquals("", State.normalise(""));
        assertEquals("", State.normalise("  "));
        try {
            State.normalise(null);
            fail("DataException expected");
        } catch (DataException e) {
            // Expected
        }
    }

    @Test
    public void checkValid() {
        assertTrue(State.isValid("  vic  "));
        assertFalse(State.isValid(""));
        assertFalse(State.isValid(""));
        assertFalse(State.isValid(null));
    }
}