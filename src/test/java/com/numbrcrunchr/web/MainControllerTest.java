package com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import com.numbrcrunchr.domain.MathUtilTest;
import com.numbrcrunchr.domain.Property;
import com.numbrcrunchr.domain.PropertyTest;
import com.numbrcrunchr.domain.State;
import com.numbrcrunchr.web.MainController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
@WebAppConfiguration
public class MainControllerTest {
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
        property.setState(State.VIC);
        controller.setProperty(property);
        controller.setNumberOfYears(4);
        controller.runProjection();

        ChartSeries income = controller.getCashflowChart().getSeries().get(0);
        ChartSeries expense = controller.getCashflowChart().getSeries().get(1);
        ChartSeries outOfPocket = controller.getCashflowChart().getSeries()
                .get(2);
        assertNotNull(income);
        assertNotNull(expense);
        assertNotNull(outOfPocket);
        // TODO Why do these tests return different results every day?
        // assertEquals(
        // "{Y 1 (partial)=4052.1978021978025, Y 2=13000.0, Y 3=13520.000000000002, Y 4=14060.800000000003}",
        // income.getData().toString());
        // assertEquals(
        // "{Y 1 (partial)=19469.213626373625, Y 2=43940.32, Y 3=44196.26, Y 4=44460.3982}",
        // expense.getData().toString());
        // assertEquals(
        // "{Y 1 (partial)=15417.015824175822, Y 2=30940.32, Y 3=30676.260000000002, Y 4=30399.5982}",
        // controller.getCashflowChart().getSeries().get(2).getData()
        // .toString());
    }

    @Test
    public void checkMainControllerSimilarToTheWebApp() {
        controller.setPropertyValue(320000l);
        controller.setWeeklyRent(320l);
        controller.setState(State.VIC);
        assertNull(controller.runProjection());

        assertNotNull(controller.getProjection());
        assertTrue(controller.isShowResults());
    }

    @Test
    public void checkThatDeserialisedObjectIsValid()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream("MainController.ser"));
        stream.writeObject(controller);
        stream.close();

        Object object = new ObjectInputStream(new FileInputStream(
                "MainController.ser")).readObject();
        assertNotNull(object);
        assertTrue(object instanceof MainController);
        MainController deSerialisedController = (MainController) object;
        assertEquals(controller.getAnnualIncome(),
                deSerialisedController.getAnnualIncome());
        assertEquals(controller.getBuildingInspections(),
                deSerialisedController.getBuildingInspections());
        assertEquals(controller.getBuildingValue(),
                deSerialisedController.getBuildingValue());
        assertEquals(controller.getCapitalGrowthRate(),
                deSerialisedController.getCapitalGrowthRate(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertNotNull(deSerialisedController.getStampDutyCalculator());

        // TODO When MainController is deserialised, spring dependencies=NULL
        // deSerialisedController.getStampDutyCalculator().calculateStampDuty(
        // State.NSW, 500000d);
        // assertNull(deSerialisedController.runProjection());
        // assertNotNull(deSerialisedController.getProjection());
        // assertTrue(deSerialisedController.isShowResults());
    }
}
