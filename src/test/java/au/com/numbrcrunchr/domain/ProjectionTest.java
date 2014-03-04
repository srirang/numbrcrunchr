package au.com.numbrcrunchr.domain;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class ProjectionTest {

    @Autowired
    private FeasibilityAnalysisProjectionService projectionService;
    @Autowired
    private ProjectionParameters projectionParameters;

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
        long income = 120000;
        long ongoingCosts = 7000;
        long weeklyRent = 320;
        byte weeksRented = 50;
        long loanAmount = 427320;
        double interestRate = 8;
        double propertyManagementFee = 10;
        Property property = PropertyTest.createProperty(income, true,
                loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee);
        Projection projection = projectionService.runProjection(property, 50,
                projectionParameters);
        List<FeasibilityAnalysisResult> projections = projection
                .getProjections();
        System.out.println(projection);
        try {
            new Projection(projections, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }
}
