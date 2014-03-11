package com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.numbrcrunchr.domain.FeasibilityAnalyser;
import com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import com.numbrcrunchr.domain.FinancialYearUtils;
import com.numbrcrunchr.domain.Owner;
import com.numbrcrunchr.domain.Property;

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
        result = feasibilityAnalyser.analyseFeasibility(
                createProperty(income, false, loanAmount, ongoingCosts,
                        weeksRented, weeklyRent, interestRate,
                        propertyManagementFee), "Year 1");

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(32347, result.getAnnualTaxBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(87653, result.getTotalNettIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(16000, result.getRentalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-18185.6, result.getGrossCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(101814.4, result.getAnnualIncomeAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(27145, result.getAnnualTaxAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(5202, result.getTaxSavings(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-12983.599999999999, result.getNettCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-12983.599999999999, result.getYouPay(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(16000, result.getTotalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(44385.6, result.getTotalExpense(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals("Year 1", result.getYear());
    }

    @Test
    public void checkNegativeCashflowPropertyWithProRataFirstYear() {
        Date purchaseDate = new DateMidnight(2010, 6, 10).toDate();

        double proRataFinancialYearRemaining = 0.054945054945054944;
        assertEquals(proRataFinancialYearRemaining,
                FinancialYearUtils
                        .getFinancialYearRatioRemainingOn(purchaseDate),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);

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
                result.getAnnualIncomeBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(1777.3076923076924, result.getAnnualTaxBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(4816.098901098901, result.getTotalNettIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(879.1208791208791, result.getRentalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-999.2087912087911, result.getGrossCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(5594.197802197802, result.getAnnualIncomeAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(1491.4835164835165, result.getAnnualTaxAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(285.8241758241758, result.getTaxSavings(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-713.3846153846152, result.getNettCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-713.3846153846152, result.getYouPay(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
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
        result = feasibilityAnalyser.analyseFeasibility(
                createProperty(income, false, loanAmount, ongoingCosts,
                        weeksRented, weeklyRent, interestRate,
                        propertyManagementFee), "Year X");

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(32347, result.getAnnualTaxBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(87653, result.getTotalNettIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(16000, result.getRentalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-7040, result.getGrossCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(112960.0, result.getAnnualIncomeAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(31436, result.getAnnualTaxAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(911, result.getTaxSavings(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-6129, result.getNettCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-6129, result.getYouPay(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals("Year X", result.getYear());
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
        result = feasibilityAnalyser.analyseFeasibility(
                createProperty(income, false, loanAmount, ongoingCosts,
                        weeksRented, weeklyRent, interestRate,
                        propertyManagementFee), "Y1");

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.POSITIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(32347, result.getAnnualTaxBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(87653, result.getTotalNettIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(16000, result.getRentalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(9600, result.getGrossCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(129600, result.getAnnualIncomeAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(37843, result.getAnnualTaxAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-5496, result.getTaxSavings(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(4104, result.getNettCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(4104, result.getYouPay(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals("Y1", result.getYear());
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
        result = feasibilityAnalyser.analyseFeasibility(
                createProperty(income, false, loanAmount, ongoingCosts,
                        weeksRented, weeklyRent, interestRate,
                        propertyManagementFee), "1");

        assertNotNull(result);
        assertEquals(FeasibilityAnalysisResult.NEGATIVE, result.getGearing());
        assertEquals(income, result.getAnnualIncomeBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(108547, result.getAnnualTaxBeforeIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(191453, result.getTotalNettIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(22500, result.getRentalIncome(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-11685.59999999999, result.getGrossCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(288314.4, result.getAnnualIncomeAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(107613, result.getAnnualTaxAfterIP(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(934, result.getTaxSavings(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-10751.599999999999, result.getNettCashflow(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(-10751.599999999999, result.getYouPay(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals("1", result.getYear());
    }

    public static Property createProperty(long annualIncome,
            boolean medicareLevyApplies, long loanAmount, long ongoingCosts,
            byte weeksRented, long weeklyRent, double interestRate,
            double managementFeeRate) {
        Property property = new Property();
        property.setPurchasePrice(loanAmount);
        property.setLoanAmount(loanAmount);
        property.setInterestRate(interestRate);
        property.setWeeklyRent(weeklyRent);
        property.setWeeksRented(weeksRented);
        property.setCouncilRates(ongoingCosts);
        property.setManagementFeeRate(managementFeeRate);
        Owner owner = new Owner();
        owner.setAnnualIncome(annualIncome);
        owner.setMedicareLevyApplies(medicareLevyApplies);
        property.addOwner(owner);
        return property;
    }
}
