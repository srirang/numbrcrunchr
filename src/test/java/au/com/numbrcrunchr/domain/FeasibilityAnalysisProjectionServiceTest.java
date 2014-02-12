package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.numbrcrunchr.CsvExporter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class FeasibilityAnalysisProjectionServiceTest {
    private static final Logger LOGGER = Logger
            .getLogger(FeasibilityAnalysisProjectionServiceTest.class.getName());
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
    private double propertyManagementFee;
    private long landlordInsurance;
    private long maintenance;
    private long strata;
    private long waterRates;
    private long cleaning;
    private long councilRates;
    private long gardening;
    private long taxExpenses;
    private long miscOngoingExpenses;
    private double capitalGrowthRate;
    private double cpi;
    private double salaryIncreaseRate;
    private double rentIncreaseRate;
    private int loanTerm;
    private int interestOnlyPeriod;
    private int projectionYears;

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
        LOGGER.info(CsvExporter.exportToCsvString(projections));
    }

    @Test
    public void checkProjectionFor320kPropertyAt320PerWeekOver25Years() {
        income = 100000;
        weeklyRent = 320;
        weeksRented = 50;
        loanAmount = 320000;
        interestRate = 6;
        landlordInsurance = 400;
        maintenance = 100;
        strata = 0;
        waterRates = 800;
        cleaning = 100;
        councilRates = 1500;
        gardening = 100;
        taxExpenses = 100;
        propertyManagementFee = 8.8;
        miscOngoingExpenses = 0;
        projectionYears = 25;
        cpi = 3;
        capitalGrowthRate = 8;
        salaryIncreaseRate = 3.5;
        rentIncreaseRate = 4;
        loanTerm = 30;
        interestOnlyPeriod = 10;
        Date purchaseDate = new DateMidnight(2014, 2, 11).toDate();

        ProjectionParameters projectionParameters = new ProjectionParameters();
        projectionParameters.setCapitalGrowthRate(capitalGrowthRate);
        projectionParameters.setCpi(cpi);
        projectionParameters.setRentIncreaseRate(rentIncreaseRate);
        projectionParameters.setSalaryIncreaseRate(salaryIncreaseRate);
        long ongoingCosts = landlordInsurance + maintenance + strata
                + waterRates + cleaning + councilRates + gardening
                + taxExpenses + miscOngoingExpenses;

        Property property = FeasibilityAnalyserTest.createProperty(income,
                true, loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee);
        property.setLoanTerm(loanTerm);
        property.setPurchaseDate(purchaseDate);
        property.setInterestOnlyPeriod(interestOnlyPeriod);
        List<FeasibilityAnalysisResult> projections = projectionService
                .applyProjectionFor(property, projectionYears,
                        projectionParameters).getProjections();
        LOGGER.info(CsvExporter.exportToCsvString(projections));
    }
}
