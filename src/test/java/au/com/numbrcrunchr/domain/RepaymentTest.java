package au.com.numbrcrunchr.domain;

import static org.junit.Assert.fail;

import org.junit.Test;

public class RepaymentTest {
    @Test
    public void testEquals() {
        try {
            new Repayment(0, 0, 0, 0).equals(new Repayment(0, 0, 0, 0));
            fail("UnsupportedOperationException expected");
        } catch (UnsupportedOperationException e) {

        }
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
