package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilTest {
    public static double ROUNDING_ERROR_TOLERANCE = 0.00000001;

    @Test
    public void checkIncreaseBy() {
        assertEquals(1.1, MathUtil.increaseBy(1, 10),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(110, MathUtil.increaseBy(100, 10),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
    }
}
