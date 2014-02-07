package au.com.numbrcrunchr.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.numbrcrunchr.domain.FeasibilityAnalyserTest;
import au.com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import au.com.numbrcrunchr.domain.Property;
import au.com.numbrcrunchr.domain.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
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
		assertEquals("Negative", controller.getGearing());
	}

	@Test
	public void performSomeAnalysis() {
		Property property = FeasibilityAnalyserTest.createProperty(300000l,
				true, 427320l, 5000l, Byte.valueOf("50"), 450l, 8.0, 10.0);
		property.setState(State.VIC);
		controller.setProperty(property);
		controller.performFeasibilityAnalysis();

		List<FeasibilityAnalysisResult> projection = controller.getProjection();
		assertEquals(25, projection.size());
		assertEquals("Negative", controller.getGearing());
	}

	@Test
	public void performSomeNegativeAnalysis() {
		Property property = FeasibilityAnalyserTest.createProperty(300000l,
				true, 427320l, 5000l, Byte.valueOf("50"), 10l, 8.0, 10.0);
		property.setState(State.VIC);
		controller.setProperty(property);
		controller.performFeasibilityAnalysis();

		List<FeasibilityAnalysisResult> projection = controller.getProjection();
		assertEquals(25, projection.size());
		assertEquals("Negative", controller.getGearing());
	}

	@Test
	public void performSomeAnalysisWhenStampDutyIsIncluded() {
		Property property = FeasibilityAnalyserTest.createProperty(300000l,
				true, 427320l, 5000l, Byte.valueOf("50"), 450l, 8.0, 10.0);
		property.setState(State.VIC);
		controller.setProperty(property);
		controller.setIncludesStampDuty(true);
		controller.performFeasibilityAnalysis();

		List<FeasibilityAnalysisResult> projection = controller.getProjection();
		assertEquals(25, projection.size());
	}

	public void checkJson() {
		// TODO Why does this fail intermittently?
		Property property = FeasibilityAnalyserTest.createProperty(125000,
				true, 427320, 5000, (byte) 50, 250, 8.0, 10.0);
		System.out.println(property);
		property.setState(State.VIC);
		controller.setProperty(property);
		controller.setNumberOfYears(4);
		controller.performFeasibilityAnalysis();

		controller.getProjection();
		assertEquals("[20471,24223,24578,24970]",
				controller.getAllIncomesAsJson());
		assertEquals("[36048,42151,42274,42412]",
				controller.getAllExpensesAsJson());
		assertEquals("[15577,17928,17696,17442]",
				controller.getAllOutOfPocketsAsJson());
		assertEquals("['Year 1', 'Year 2', 'Year 3', 'Year 4']",
				controller.getJsonYears());
	}

}