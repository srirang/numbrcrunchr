package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
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
}