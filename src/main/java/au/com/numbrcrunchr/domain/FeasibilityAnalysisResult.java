package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeasibilityAnalysisResult implements Serializable {

    private static final long serialVersionUID = 1908092070730909381L;

    public static final String POSITIVE = "Positive";
    public static final String NEGATIVE = "Negative";
    public static final String NEUTRAL = "Neutral";

    public static final String YEARLY = "Yearly";
    public static final String HALF_YEARLY = "Half-Yearly";
    public static final String QUARTERLY = "Quarterly";
    public static final String MONTHLY = "Monthly";
    public static final String FORTNIGHTLY = "Fortnightly";
    public static final String WEEKLY = "Weekly";
    public static final String DAILY = "Daily";

    public static final Map<String, Double> FREQUENCY_FACTORS;
    static {
        FREQUENCY_FACTORS = new HashMap<String, Double>();
        FREQUENCY_FACTORS.put(YEARLY, 1.0);
        FREQUENCY_FACTORS.put(HALF_YEARLY, 1.0 / 2.0);
        FREQUENCY_FACTORS.put(QUARTERLY, 1.0 / 4.0);
        FREQUENCY_FACTORS.put(MONTHLY, 1.0 / 12.0);
        FREQUENCY_FACTORS.put(FORTNIGHTLY, 1.0 / 26.0);
        FREQUENCY_FACTORS.put(WEEKLY, 1.0 / 52.0);
        FREQUENCY_FACTORS.put(DAILY, 1.0 / 365.0);
    }
    public static final ArrayList<String> FREQUENCIES;
    static {
        FREQUENCIES = new ArrayList<String>(5);
        FREQUENCIES.add(YEARLY);
        FREQUENCIES.add(HALF_YEARLY);
        FREQUENCIES.add(QUARTERLY);
        FREQUENCIES.add(MONTHLY);
        FREQUENCIES.add(FORTNIGHTLY);
        FREQUENCIES.add(WEEKLY);
        FREQUENCIES.add(DAILY);
    }

    private final Property property;
    private final double interest;
    private final double grossCashflow;
    private final double rentalIncome;
    private final double taxSavings;
    private final double youPay;
    private final double nettCashflow;
    private final double annualIncomeAfterIP;
    private final double annualTaxAfterIP;
    private final double annualIncomeBeforeIP;
    private final double annualTaxBeforeIP;
    private final double totalNettIncome;
    private final double ongoingExpenses;
    private String year;
    private String frequency = YEARLY;

    public FeasibilityAnalysisResult(Property property, double grossCashflow,
            double rentalIncome, double taxSavings, double youPay,
            double nettCashflow, double interest, double annualIncomeAfterIP,
            double annualTaxAfterIP, double annualIncomeBeforeIP,
            double annualTaxBeforeIP, double totalNettIncome) {
        super();
        this.property = property;
        this.grossCashflow = grossCashflow;
        this.rentalIncome = rentalIncome;
        this.taxSavings = taxSavings;
        this.youPay = youPay;
        this.nettCashflow = nettCashflow;
        this.interest = interest;
        this.annualIncomeAfterIP = annualIncomeAfterIP;
        this.annualTaxAfterIP = annualTaxAfterIP;
        this.annualIncomeBeforeIP = annualIncomeBeforeIP;
        this.annualTaxBeforeIP = annualTaxBeforeIP;
        this.totalNettIncome = totalNettIncome;
        // this.ongoingExpenses = ongoingExpenses;
        this.ongoingExpenses = property.getTotalOngoingCosts();
    }

    public FeasibilityAnalysisResult applyPurchaseDate(Date purchaseDate) {
        PartialPeriodDetails partialPeriodDetails = FinancialYearUtils
                .getFinancialYearRemainingOn(purchaseDate);

        double financialYearRemaining = partialPeriodDetails
                .getProRataFinancialYear();
        FeasibilityAnalysisResult newResult = new FeasibilityAnalysisResult(
                this.property, grossCashflow * financialYearRemaining,
                rentalIncome * financialYearRemaining, taxSavings
                        * financialYearRemaining, youPay
                        * financialYearRemaining, nettCashflow
                        * financialYearRemaining, interest
                        * financialYearRemaining, annualIncomeAfterIP
                        * financialYearRemaining, annualTaxAfterIP
                        * financialYearRemaining, annualIncomeBeforeIP
                        * financialYearRemaining, annualTaxBeforeIP
                        * financialYearRemaining, totalNettIncome
                        * financialYearRemaining);
        return newResult;
    }

    public double getTaxSavings() {
        return frequencify(taxSavings);
    }

    public double getGrossCashflow() {
        return frequencify(grossCashflow);
    }

    private double frequencify(double aNumber) {
        return FREQUENCY_FACTORS.get(frequency) * aNumber;
    }

    public double getRentalIncome() {
        return frequencify(rentalIncome);
    }

    public double getYouPay() {
        return frequencify(youPay);
    }

    public String getGearing() {
        String gearing;
        if (grossCashflow < 0) {
            gearing = NEGATIVE;
        } else if (grossCashflow > 0) {
            gearing = POSITIVE;
        } else {
            gearing = NEUTRAL;
        }
        return gearing;
    }

    public boolean isPositiveCashflow() {
        return POSITIVE.equals(getGearing());
    }

    public double getInterest() {
        return frequencify(interest);
    }

    public double getOngoingExpenses() {
        return frequencify(ongoingExpenses);
    }

    public double getAnnualIncomeAfterIP() {
        return frequencify(annualIncomeAfterIP);
    }

    public double getAnnualTaxAfterIP() {
        return frequencify(annualTaxAfterIP);
    }

    public double getAnnualIncomeBeforeIP() {
        return frequencify(annualIncomeBeforeIP);
    }

    public double getAnnualTaxBeforeIP() {
        return frequencify(annualTaxBeforeIP);
    }

    public double getNettCashflow() {
        return frequencify(nettCashflow);
    }

    public double getTotalNettIncome() {
        return frequencify(totalNettIncome);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public double getTotalExpense() {
        return getOngoingExpenses() + getInterest();
    }

    public double getTotalIncome() {
        return getRentalIncome();
    }

    public double getTotalOutOfPocket() {
        double outOfPocket = getTotalExpense() - getTotalIncome();
        if (outOfPocket < 0) {
            return 0;
        }
        return outOfPocket;
    }

    public double getGrossYield() {
        return MathUtil.scaled(100 * getAnnualRentalIncome()
                / property.getPurchasePrice());
    }

    public double getNettYield() {
        return MathUtil.scaled(100
                * (getAnnualRentalIncome() - getAnnualOngoingExpenses())
                / property.getPurchasePrice());
    }

    private double getAnnualOngoingExpenses() {
        return this.ongoingExpenses;
    }

    private double getAnnualRentalIncome() {
        return this.rentalIncome;
    }

    Property getProperty() {
        return property;
    }

    public void changeFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getWeeklyRent() {
        return this.property.getWeeklyRent();
    }

    // TODO: Loan balance does not go down for P+I?
    public double getLoanBalance() {
        return this.property.getLoanAmount();
    }

    public double getPropertyValue() {
        return this.property.getMarketValue();
    }

    public double getCapitalGrowth() {
        return this.property.getMarketValue()
                - this.property.getPurchasePrice();
    }

    public double getEquityAvailable() {
        return new EquityCalculator().calculateEquity(
                this.property.getMarketValue(), getLoanBalance());
    }

    public double getCleaning() {
        return frequencify(property.getCleaning());
    }

    public double getMaintenance() {
        return frequencify(property.getMaintenance());
    }

    public double getLandlordsInsurance() {
        return frequencify(property.getLandlordsInsurance());
    }

    public double getStrata() {
        return frequencify(property.getStrata());
    }

    public double getCouncilRates() {
        return frequencify(property.getCouncilRates());
    }

    public double getWaterCharges() {
        return frequencify(property.getWaterCharges());
    }

    public double getGardening() {
        return frequencify(property.getGardening());
    }

    public double getTaxExpenses() {
        return frequencify(property.getTaxExpenses());
    }

    public double getPropertyManagementFees() {
        return frequencify(property.getPropertyManagementFees());
    }

}
