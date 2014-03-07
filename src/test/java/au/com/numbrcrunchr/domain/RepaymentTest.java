package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RepaymentTest {
    @Test
    public void testEquals() {
        Repayment repayment = new Repayment(0, 0, 0, 0);
        assertFalse(new Repayment(0, 0, 0, 0).equals(null));
        assertFalse(new Repayment(0, 0, 0, 0).equals("Test"));
        assertTrue(repayment.equals(repayment));
        assertTrue(new Repayment(0, 0, 0, 0).equals(new Repayment(0, 0, 0, 0)));
        assertFalse(new Repayment(0, 0, 0, 0)
                .equals(new Repayment(0, 0, 0, 10)));
        assertFalse(new Repayment(0, 0, 0, 0)
                .equals(new Repayment(0, 0, 10, 0)));
        assertFalse(new Repayment(0, 0, 0, 0)
                .equals(new Repayment(0, 01, 10, 0)));
        assertFalse(new Repayment(0, 0, 0, 0)
                .equals(new Repayment(10, 0, 10, 0)));
    }

    @Test
    public void testHascode() {
        try {
            new Repayment(0, 0, 0, 0).hashCode();
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {

        }
    }
}
