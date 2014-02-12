package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.numbrcrunchr.CsvExporter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class CsvExporterTest {
    private static final Logger LOGGER = Logger.getLogger(CsvExporterTest.class
            .getName());
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
    public void checkCsv() {
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
                .applyProjectionFor(property, 25, projectionParameters)
                .getProjections();
        assertNotNull(projections);
        assertEquals(26, projections.size());
        LOGGER.info(CsvExporter.exportToCsvString(projections));
    }
}
