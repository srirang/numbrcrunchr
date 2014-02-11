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
        long tax, nettIncome;
        long totalGrossIncome = property.calculateOwnerGrossIncome();

        // Base variables
        long totalTax = 0;
        long totalNettIncome = 0;
        for (Owner owner : property.getOwnerList()) {
            tax = taxCalculator.calculateTax(new Date(),
                    owner.getAnnualIncome(), owner.getMedicareLevyApplies());
            nettIncome = taxCalculator.calculateNettIncome(new Date(),
                    owner.getAnnualIncome(), owner.getMedicareLevyApplies());
            owner.setTax(tax);
            totalTax += tax;
            totalNettIncome += nettIncome;
        }

        long rentalIncome = rentalIncomeCalculator.calculateGrossAnnualRent(
                property.getWeeklyRent(), property.getWeeksRented());
        double managementFee = rentalIncomeCalculator.calculateAnnualFee(
                property.getWeeklyRent(), property.getPropertyManagementFees(),
                property.getWeeksRented());
        double ongoingExpenses = property.calculateTotalOngoingCost()
                + managementFee;

        double totalExpenses = interest + ongoingExpenses;

        double grossCashflow = rentalIncome - totalExpenses;
        long annualIncomeBeforeIP = totalGrossIncome;
        long annualTaxBeforeIP = totalTax;

        long annualIncomeAfterIP = (long) (totalGrossIncome + grossCashflow);
        long annualTaxAfterIP = taxCalculator.calculateTax(new Date(),
                annualIncomeAfterIP, true);

        long taxSavings = annualTaxBeforeIP - annualTaxAfterIP;

        long nettCashflow = (long) (grossCashflow + taxSavings);
        long youPay = nettCashflow;

        return new FeasibilityAnalysisResult(property,
                MathUtil.doubleToLong(grossCashflow), rentalIncome, taxSavings,
                youPay, nettCashflow, MathUtil.doubleToLong(interest),
                annualIncomeAfterIP, annualTaxAfterIP, annualIncomeBeforeIP,
                annualTaxBeforeIP, totalNettIncome,
                MathUtil.doubleToLong(ongoingExpenses));
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
