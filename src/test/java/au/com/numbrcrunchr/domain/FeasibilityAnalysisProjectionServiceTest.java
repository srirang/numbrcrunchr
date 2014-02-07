package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.numbrcrunchr.CsvExporter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class FeasibilityAnalysisProjectionServiceTest {
	@Autowired
	private FeasibilityAnalysisProjectionService projectionService;
	@Autowired
	private ProjectionParameters projectionParameters;

	private long income;
	private long ongoingCosts;
	private long weeklyRent;
	private byte weeksRented;
	private long loanAmount;
	private double interestRate;
	private int propertyManagementFee;

	@Test
	public void checkProjectionFor1Year() {
		income = 120000;
		ongoingCosts = 7000;
		weeklyRent = 320;
		weeksRented = 50;
		loanAmount = 427320;
		interestRate = 8;
		propertyManagementFee = 10;
		Property property = FeasibilityAnalyserTest.createProperty(income,
				true, loanAmount, ongoingCosts, weeksRented, weeklyRent,
				interestRate, propertyManagementFee);
		List<FeasibilityAnalysisResult> projections = projectionService
				.applyProjectionFor(property, 1, projectionParameters)
				.getProjections();
		assertEquals(2, projections.size());
	}

	@Test
	public void checkProjectionFor2Years() {
		income = 120000;
		ongoingCosts = 7000;
		weeklyRent = 320;
		weeksRented = 50;
		loanAmount = 427320;
		interestRate = 8;
		propertyManagementFee = 10;
		Property property = FeasibilityAnalyserTest.createProperty(income,
				true, loanAmount, ongoingCosts, weeksRented, weeklyRent,
				interestRate, propertyManagementFee);
		List<FeasibilityAnalysisResult> projections = projectionService
				.applyProjectionFor(property, 2, projectionParameters)
				.getProjections();
		assertEquals(3, projections.size());
	}

	@Test
	public void checkProjectionFor20Years() {
		income = 120000;
		ongoingCosts = 7000;
		weeklyRent = 320;
		weeksRented = 50;
		loanAmount = 427320;
		interestRate = 8;
		propertyManagementFee = 10;
		Property property = FeasibilityAnalyserTest.createProperty(income,
				true, loanAmount, ongoingCosts, weeksRented, weeklyRent,
				interestRate, propertyManagementFee);
		List<FeasibilityAnalysisResult> projections = projectionService
				.applyProjectionFor(property, 20, projectionParameters)
				.getProjections();
		assertEquals(21, projections.size());
		System.out.println(new CsvExporter().exportToCsvString(projections));
	}
}
