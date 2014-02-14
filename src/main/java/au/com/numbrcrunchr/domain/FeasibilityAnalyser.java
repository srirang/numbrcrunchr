package au.com.numbrcrunchr.domain;

import java.util.Date;

public class FeasibilityAnalyser {

    /**
     * All figures are annual
     * 
     * @param property
     * @return
     */
    public FeasibilityAnalysisResult analyseFeasibility(Property property) {
        double interest = interestCalculator.calculateInterest(
                property.getLoanAmount(), property.getInterestRate());
        return analyseFeasibility(property, interest, 0);
    }

    public FeasibilityAnalysisResult analyseFeasibility(Property property,
            double interest, double principal) {
        double tax, nettIncome;
        double totalGrossIncome = property.calculateOwnerGrossIncome();

        // Base variables
        double totalTax = 0;
        double totalNettIncome = 0;
        for (Owner owner : property.getOwnerList()) {
            tax = taxCalculator.calculateTax(new Date(),
                    owner.getAnnualIncome(), owner.getMedicareLevyApplies());
            nettIncome = taxCalculator.calculateNettIncome(new Date(),
                    owner.getAnnualIncome(), owner.getMedicareLevyApplies());
            owner.setTax(tax);
            totalTax += tax;
            totalNettIncome += nettIncome;
        }

        double rentalIncome = rentalIncomeCalculator.calculateGrossAnnualRent(
                property.getWeeklyRent(), property.getWeeksRented());
        double ongoingExpenses = property.getTotalOngoingCosts();

        double totalExpenses = interest + ongoingExpenses;

        double grossCashflow = rentalIncome - totalExpenses;
        double annualIncomeBeforeIP = totalGrossIncome;
        double annualTaxBeforeIP = totalTax;

        double annualIncomeAfterIP = totalGrossIncome + grossCashflow;
        double annualTaxAfterIP = taxCalculator.calculateTax(new Date(),
                annualIncomeAfterIP, true);

        double taxSavings = annualTaxBeforeIP - annualTaxAfterIP;

        double nettCashflow = grossCashflow + taxSavings;
        double youPay = nettCashflow;

        return new FeasibilityAnalysisResult(property, grossCashflow,
                rentalIncome, taxSavings, youPay, nettCashflow, interest,
                annualIncomeAfterIP, annualTaxAfterIP, annualIncomeBeforeIP,
                annualTaxBeforeIP, totalNettIncome, ongoingExpenses);
    }

    public FeasibilityAnalysisResult analyseFirstYearFeasibility(
            Property property) {
        FeasibilityAnalysisResult fullYearResult = analyseFeasibility(property);
        FeasibilityAnalysisResult firstYearResult = fullYearResult
                .applyPurchaseDate(property.getPurchaseDate());
        firstYearResult.setYear("Year 1");
        return firstYearResult;
    }

    private TaxCalculator taxCalculator;
    private RentalIncomeCalculator rentalIncomeCalculator;
    private InterestOnlyLoanRepaymentCalculator interestCalculator;

    public void setTaxCalculator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public void setRentalIncomeCalculator(
            RentalIncomeCalculator rentalIncomeCalculator) {
        this.rentalIncomeCalculator = rentalIncomeCalculator;
    }

    public void setInterestCalculator(
            InterestOnlyLoanRepaymentCalculator interestCalculator) {
        this.interestCalculator = interestCalculator;
    }
}
