package com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Projection implements Serializable {
    private static final long serialVersionUID = -5278362123171908543L;

    private List<FeasibilityAnalysisResult> projections;
    private int cashflowPositiveYearIndex;
    private ProjectionParameters projectionParameters;
    private Property property;

    public static final Projection EMPTY_PROJECTION;
    static {
        EMPTY_PROJECTION = new Projection();
        EMPTY_PROJECTION.projections = Collections.emptyList();
    }

    private Projection() {

    }

    public Projection(List<FeasibilityAnalysisResult> projections,
            ProjectionParameters projectionParameters) {
        if (projections == null || projections.isEmpty()) {
            throw new IllegalArgumentException(
                    "Projection needs at least one result to create");
        }
        if (projectionParameters == null) {
            throw new IllegalArgumentException(
                    "Projection needs projectionParameters");
        }
        this.projectionParameters = projectionParameters;
        this.projections = Collections.unmodifiableList(projections);
        for (int i = 0; i < projections.size(); i++) {
            FeasibilityAnalysisResult result = projections.get(i);
            if (result.isPositiveCashflow()) {
                cashflowPositiveYearIndex = i;
                break;
            }
        }
        this.property = projections.get(0).getProperty();
    }

    public List<FeasibilityAnalysisResult> getProjections() {
        return projections;
    }

    public int getCashflowPositiveYearIndex() {
        return cashflowPositiveYearIndex;
    }

    @Override
    public String toString() {
        Property property = projections.get(0).getProperty();
        StringBuffer sb = new StringBuffer("Projection Results:");
        sb.append("\n\n\n\n---------------------------------------------------------------------------");
        sb.append("\nAssumptions:");
        sb.append("\nCPI: \t").append(this.projectionParameters.getCpi());
        sb.append("\nCapital Growth Rate: \t").append(
                this.projectionParameters.getCapitalGrowthRate());
        sb.append("\nRent Increase Rate: \t").append(
                this.projectionParameters.getRentIncreaseRate());
        sb.append("\nPAYG Income Increase Rate: \t").append(
                this.projectionParameters.getSalaryIncreaseRate());
        sb.append("\nProperty Management Rate: \t").append(
                property.getManagementFeeRate());
        sb.append("\nOwner PAYG Income: \t").append(
                property.getOwnerList().get(0).getAnnualIncome());
        sb.append("\nDays in First Financial Year: \t").append(
                FinancialYearUtils.getFinancialYearRemainingOn(
                        property.getPurchaseDate())
                        .getDaysInFirstFinancialYear());
        sb.append("\n");
        sb.append("\nProperty Details: ");
        sb.append("\nPurchase Price: \t").append(property.getPurchasePrice());
        sb.append("\nStamp Duty & Govt Charges: \t").append(
                property.getGovermentCosts());
        sb.append("\nPurchase Date: \t").append(property.getPurchaseDate());
        sb.append("\nState: \t").append(property.getState());
        sb.append("\nWeeks Rented: \t").append(property.getWeeksRented());
        sb.append("\nTotal Purchase Cost: \t").append(
                property.getTotalPurchaseCost());
        sb.append("\n");
        sb.append("\nLoan Details: ");
        sb.append("\nLoam Amount: \t").append(property.getLoanAmount());
        sb.append("\nLoam Term: \t").append(property.getLoanTerm());
        sb.append("\nInterest Rate: \t").append(property.getInterestRate());
        sb.append("\nInterest Only Period: \t")
                .append(property.getInterestOnlyPeriod()).append(" Years");
        sb.append("\nLMI: \t").append(property.getLendersMortgageInsurance());
        sb.append("\nLVR: \t").append(property.getLvr());
        sb.append("\n");

        sb.append("\nYears: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getYear()).append("\t");
        }
        sb.append("\nLoan Balance: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getLoanBalance()).append("\t");
        }
        sb.append("\nWeekly Rent: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getWeeklyRent()).append("\t");
        }
        sb.append("\nRental Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getRentalIncome()).append("\t");
        }
        sb.append("\nInterest: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getInterest()).append("\t");
        }
        sb.append("\nLandlord's Insurance: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getLandlordsInsurance()).append("\t");
        }
        sb.append("\nMaintenance: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getMaintenance()).append("\t");
        }
        sb.append("\nStrata: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getStrata()).append("\t");
        }
        sb.append("\nWater Rates: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getWaterCharges()).append("\t");
        }
        sb.append("\nCleaning: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getCleaning()).append("\t");
        }
        sb.append("\nCouncil Rates: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getCouncilRates()).append("\t");
        }
        sb.append("\nGardening: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getGardening()).append("\t");
        }
        sb.append("\nTax Expenses: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getTaxExpenses()).append("\t");
        }
        sb.append("\nProperty Mgmt Fee: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getPropertyManagementFees()).append("\t");
        }

        sb.append("\n");

        sb.append("\nTotal Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getTotalIncome()).append("\t");
        }
        sb.append("\nTotal Expense: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getTotalExpense()).append("\t");
        }

        sb.append("\nPAYG Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(
                    result.getProperty().getOwnerList().get(0)
                            .getAnnualIncome()).append("\t");
        }
        sb.append("\nMarket Value: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getProperty().getMarketValue()).append("\t");
        }

        sb.append("\n");

        sb.append("\nProfit/Loss: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getGrossCashflow()).append("\t");
        }
        sb.append("\nTaxable Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getAnnualIncomeAfterIP()).append("\t");
        }
        sb.append("\nTax on Taxable Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getAnnualTaxAfterIP()).append("\t");
        }
        sb.append("\nTax on PAYG Income: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getAnnualTaxBeforeIP()).append("\t");
        }
        sb.append("\nTax Refund: \t");
        for (FeasibilityAnalysisResult result : projections) {
            sb.append(result.getTaxSavings()).append("\t");
        }

        sb.append("\n---------------------------------------------------------------------------\n\n\n\n");
        return sb.toString();
    }

    public void changeFrequency(String frequency) {
        for (FeasibilityAnalysisResult result : projections) {
            result.changeFrequency(frequency);
        }
    }

    protected Property getProperty() {
        return property;
    }
}
