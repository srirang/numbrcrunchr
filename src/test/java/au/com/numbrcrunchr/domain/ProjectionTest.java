package au.com.numbrcrunchr.domain;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

public class ProjectionTest {

    @Test
    public void checkWithNull() {
        try {
            new Projection(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
        try {
            new Projection(new ArrayList<FeasibilityAnalysisResult>(), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }
}
