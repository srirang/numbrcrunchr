package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class FeasibilityAnalyserTest {

    @Autowired
    private FeasibilityAnalyser feasibilityAnalyser;
    private long income;
    private long ongoingCosts;
    private long weeklyRent;
    private byte weeksRented;
    private long loanAmount;
    private double interestRate;
    private int propertyManagementFee;
    private FeasibilityAnalysisResult result;

    @Test
    public void checkNegativeCashflowProperty() {
        income = 120000;
        ongoingCosts = 7000;
        weeklyRent = 320;
        weeksRented = 50;
        loanAmount = 427320;
        interestRate = 8;
        propertyManagementFee = 10;
        result = feasibilityAnalyser.analyseFeasibility(createProperty(income,
                false, loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee));

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(), 0);
        assertEquals(32350, result.getAnnualTaxBeforeIP(), 0);
        assertEquals(87650, result.getTotalNettIncome(), 0);
        assertEquals(16000, result.getRentalIncome(), 0);
        assertEquals(-28386, result.getGrossCashflow(), 0);
        assertEquals(91614, result.getAnnualIncomeAfterIP(), 0);
        assertEquals(23221, result.getAnnualTaxAfterIP(), 0);
        assertEquals(9129, result.getTaxSavings(), 0);
        assertEquals(-19256, result.getNettCashflow(), 0);
        assertEquals(-19256, result.getYouPay(), 0);
        assertEquals(25129, result.getTotalIncome(), 0);
        assertEquals(44386, result.getTotalExpense(), 0);
    }

    @Test
    public void checkNegativeCashflowPropertyWithProRataFirstYear() {
        Date purchaseDate = new DateMidnight(2010, 6, 10).toDate();

        double proRataFinancialYearRemaining = 0.054945054945054944;
        assertEquals(proRataFinancialYearRemaining,
                FinancialYearUtils
                        .getFinancialYearRatioRemainingOn(purchaseDate), 0);

        income = 120000;
        ongoingCosts = 7000;
        weeklyRent = 320;
        weeksRented = 50;
        loanAmount = 427320;
        interestRate = 8;
        propertyManagementFee = 10;
        Property property = createProperty(income, false, loanAmount,
                ongoingCosts, weeksRented, weeklyRent, interestRate,
                propertyManagementFee);
        property.setPurchaseDate(purchaseDate);
        result = feasibilityAnalyser.analyseFirstYearFeasibility(property);

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income * proRataFinancialYearRemaining,
                result.getAnnualIncomeBeforeIP(), 1);
        assertEquals(32350 * proRataFinancialYearRemaining,
                result.getAnnualTaxBeforeIP(), 1);
        assertEquals(87650 * proRataFinancialYearRemaining,
                result.getTotalNettIncome(), 1);
        assertEquals(879, result.getRentalIncome(), 1);
        assertEquals(-1560, result.getGrossCashflow(), 1);
        assertEquals(5034, result.getAnnualIncomeAfterIP(), 1);
        assertEquals(1276, result.getAnnualTaxAfterIP(), 1);
        assertEquals(502, result.getTaxSavings(), 1);
        assertEquals(-1058, result.getNettCashflow(), 1);
        assertEquals(-1058, result.getYouPay(), 1);
    }

    @Test
    public void checkAnotherNegativeCashflowProperty() {
        income = 120000;
        ongoingCosts = 2500;
        weeklyRent = 320;
        weeksRented = 50;
        loanAmount = 288000;
        interestRate = 8;
        propertyManagementFee = 10;
        result = feasibilityAnalyser.analyseFeasibility(createProperty(income,
                false, loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee));

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(), 0);
        assertEquals(32350, result.getAnnualTaxBeforeIP(), 0);
        assertEquals(87650, result.getTotalNettIncome(), 0);
        assertEquals(16000, result.getRentalIncome(), 0);
        assertEquals(-12740, result.getGrossCashflow(), 0);
        assertEquals(107260, result.getAnnualIncomeAfterIP(), 0);
        assertEquals(29245, result.getAnnualTaxAfterIP(), 0);
        assertEquals(3105, result.getTaxSavings(), 0);
        assertEquals(-9635, result.getNettCashflow(), 0);
        assertEquals(-9635, result.getYouPay(), 0);
    }

    @Test
    public void checkPositiveCashflowProperty() {
        income = 120000;
        ongoingCosts = 2500;
        weeklyRent = 320;
        weeksRented = 50;
        loanAmount = 80000;
        interestRate = 8;
        propertyManagementFee = 10;
        result = feasibilityAnalyser.analyseFeasibility(createProperty(income,
                false, loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee));

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.POSITIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(), 0);
        assertEquals(32350, result.getAnnualTaxBeforeIP(), 0);
        assertEquals(87650, result.getTotalNettIncome(), 0);
        assertEquals(16000, result.getRentalIncome(), 0);
        assertEquals(3900, result.getGrossCashflow(), 0);
        assertEquals(123900, result.getAnnualIncomeAfterIP(), 0);
        assertEquals(35651, result.getAnnualTaxAfterIP(), 0);
        assertEquals(-3301, result.getTaxSavings(), 0);
        assertEquals(599, result.getNettCashflow(), 0);
        assertEquals(599, result.getYouPay(), 0);
    }

    @Test
    public void checkMyPropertyCashflow() {
        income = 300000;
        ongoingCosts = 5700;
        weeklyRent = 450;
        weeksRented = 50;
        loanAmount = 427320;
        interestRate = 8;
        propertyManagementFee = 10;
        result = feasibilityAnalyser.analyseFeasibility(createProperty(income,
                false, loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee));

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(), 0);
        assertEquals(108550, result.getAnnualTaxBeforeIP(), 0);
        assertEquals(191450, result.getTotalNettIncome(), 0);
        assertEquals(22500, result.getRentalIncome(), 0);
        assertEquals(-21236.0, result.getGrossCashflow(), 0);
        assertEquals(278764, result.getAnnualIncomeAfterIP(), 0);
        assertEquals(103175, result.getAnnualTaxAfterIP(), 0);
        assertEquals(5375, result.getTaxSavings(), 0);
        assertEquals(-15860, result.getNettCashflow(), 0);
        assertEquals(-15860, result.getYouPay(), 0);
    }

    public static Property createProperty(long annualIncome,
            boolean medicareLevyApplies, long loanAmount, long ongoingCosts,
            byte weeksRented, long weeklyRent, double interestRate,
            double propertyManagementFee) {
        Property property = new Property();
        property.setPurchasePrice(loanAmount);
        property.setLoanAmount(loanAmount);
        property.setInterestRate(interestRate);
        property.setWeeklyRent(weeklyRent);
        property.setWeeksRented(weeksRented);
        property.setCouncilRates(ongoingCosts);
        property.setPropertyManagementFees(propertyManagementFee);
        Owner owner = new Owner();
        owner.setAnnualIncome(annualIncome);
        owner.setMedicareLevyApplies(medicareLevyApplies);
        property.addOwner(owner);
        return property;
    }
}
