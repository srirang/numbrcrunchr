package au.com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import au.com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import au.com.numbrcrunchr.domain.Property;
import au.com.numbrcrunchr.domain.PropertyTest;
import au.com.numbrcrunchr.domain.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
@WebAppConfiguration
public class MainControllerTest {
    private static final Logger LOGGER = Logger.getLogger(MainController.class
            .getName());

    @Autowired
    private MainController controller;

    @Test
    public void checkDefaults() {
        assertNotNull(controller.getProjection());
        assertNotNull(controller.getProperty());
        assertNotNull(controller.getOwner());
        assertNotNull(controller.getStates());
        assertEquals(8, controller.getStates().size());
        // assertEquals(6, controller.getInterestRate(), 0);
        assertEquals(100000l, controller.getAnnualIncome(), 0);
    }

    @Test
    public void checkDependencies() {
        assertNotNull(controller);
        assertNotNull(controller.getFeasibilityAnalysisProjectionService());
        assertNotNull(controller.getStampDutyCalculator());
        assertNotNull(controller.getLoanBalanceCalculator());
        assertNotNull(controller.getProjectionParameters());
        assertEquals("Negative", controller.getGearing());
    }

    @Test
    public void performSomeAnalysis() {
        Property property = PropertyTest.createProperty(300000l, true, 427320l,
                5000l, Byte.valueOf("50"), 450l, 8.0, 10.0);
        property.setState(State.VIC);
        controller.setProperty(property);
        controller.runProjection();

        List<FeasibilityAnalysisResult> projection = controller.getProjection();
        assertEquals(25, projection.size());
        assertEquals("Negative", controller.getGearing());
        // assertNotNull(controller.getAllExpensesAsJson());
        // assertNotNull(controller.getAllGrossYieldsAsJson());
        // assertNotNull(controller.getAllIncomesAsJson());
        // assertNotNull(controller.getAllNettYieldsAsJson());
        // assertNotNull(controller.getAllOutOfPocketsAsJson());
        assertNotNull(controller.getCashflowChart());
        assertNotNull(controller.getEquityChart());
    }

    @Test
    public void performSomeNegativeAnalysis() {
        Property property = PropertyTest.createProperty(300000l, true, 427320l,
                5000l, Byte.valueOf("50"), 10l, 8.0, 10.0);
        property.setState(State.VIC);
        controller.setProperty(property);
        controller.runProjection();

        List<FeasibilityAnalysisResult> projection = controller.getProjection();
        assertEquals(25, projection.size());
        assertEquals("Negative", controller.getGearing());
    }

    @Test
    public void performSomeAnalysisWhenStampDutyIsIncluded() {
        Property property = PropertyTest.createProperty(300000l, true, 427320l,
                5000l, Byte.valueOf("50"), 450l, 8.0, 10.0);
        property.setState(State.VIC);
        controller.setProperty(property);
        controller.setIncludesStampDuty(true);
        controller.runProjection();

        List<FeasibilityAnalysisResult> projection = controller.getProjection();
        assertEquals(25, projection.size());
    }

    @Test
    public void checkCharts() {
        Property property = PropertyTest.createProperty(125000, true, 427320,
                5000, (byte) 50, 250, 8.0, 10.0);
        LOGGER.info(String.valueOf(property));
        property.setState(State.VIC);
        controller.setProperty(property);
        controller.setNumberOfYears(4);
        controller.runProjection();

        controller.getProjection();
        // assertEquals("[20471,24223,24578,24970]",
        // controller.getAllIncomesAsJson());
        // assertEquals("[36048,42151,42274,42412]",
        // controller.getAllExpensesAsJson());
        // assertEquals("[15577,17928,17696,17442]",
        // controller.getAllOutOfPocketsAsJson());
        // assertEquals("['Year 1', 'Year 2', 'Year 3', 'Year 4']",
        // controller.getJsonYears());
        ChartSeries income = controller.getCashflowChart().getSeries().get(0);
        ChartSeries expense = controller.getCashflowChart().getSeries().get(1);
        ChartSeries outOfPocket = controller.getCashflowChart().getSeries()
                .get(2);
        assertNotNull(income);
        assertNotNull(expense);
        assertNotNull(outOfPocket);
        assertEquals(
                "{Year 1=4155.21978021978, Year 2=13000.0, Year 3=13520.000000000002, Year 4=14060.800000000003}",
                income.getData().toString());
        assertEquals(
                "{Year 1=19764.617362637364, Year 2=43940.32, Year 3=44196.26, Year 4=44460.3982}",
                expense.getData().toString());
        assertEquals(
                "{Year 1=15609.397582417583, Year 2=30940.32, Year 3=30676.260000000002, Year 4=30399.5982}",
                controller.getCashflowChart().getSeries().get(2).getData()
                        .toString());
    }

}
