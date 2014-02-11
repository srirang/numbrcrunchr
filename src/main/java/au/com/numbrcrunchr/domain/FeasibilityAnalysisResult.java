package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeasibilityAnalysisResult implements Serializable {

    private static final long serialVersionUID = 1908092070730909381L;

    public static final String POSITIVE = "Positive";
    public static final String NEGATIVE = "Negative";
    public static final String NEUTRAL = "Neutral";

    public static final String YEARLY = "Yearly";
    public static final String QUARTERLY = "Quarterly";
    public static final String MONTHLY = "Monthly";
    public static final String FORTNIGHTLY = "Fortnightly";
    public static final String WEEKLY = "Weekly";

    public static final Map<String, Double> FREQUENCY_FACTORS;
    static {
        FREQUENCY_FACTORS = new HashMap<String, Double>();
        FREQUENCY_FACTORS.put(YEARLY, 1.0);
        FREQUENCY_FACTORS.put(QUARTERLY, 1.0 / 4.0);
        FREQUENCY_FACTORS.put(MONTHLY, 1.0 / 12.0);
        FREQUENCY_FACTORS.put(FORTNIGHTLY, 1.0 / 26.0);
        FREQUENCY_FACTORS.put(WEEKLY, 1.0 / 52.0);
    }
    private final Property property;
    private final Long interest;
    private final Long grossCashflow;
    private final Long rentalIncome;
    private final Long taxSavings;
    private final Long youPay;
    private final Long nettCashflow;
    private final Long annualIncomeAfterIP;
    private final Long annualTaxAfterIP;
    private final Long annualIncomeBeforeIP;
    private final Long annualTaxBeforeIP;
    private final Long totalNettIncome;
    private final Long ongoingExpenses;
    private String year;
    private String frequency = YEARLY;

    public FeasibilityAnalysisResult(Property property, Long grossCashflow,
            Long rentalIncome, Long taxSavings, Long youPay, Long nettCashflow,
            Long interest, Long annualIncomeAfterIP, Long annualTaxAfterIP,
            Long annualIncomeBeforeIP, Long annualTaxBeforeIP,
            Long totalNettIncome, Long ongoingExpenses) {
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
        this.ongoingExpenses = ongoingExpenses;
    }

    public FeasibilityAnalysisResult applyPurchaseDate(Date effectiveDate) {
        PartialPeriodDetails partialPeriodDetails = FinancialYearUtils
                .getFinancialYearRemainingOn(effectiveDate);

        double financialYearRemaining = partialPeriodDetails
                .getProRataFinancialYear();
        FeasibilityAnalysisResult newResult = new FeasibilityAnalysisResult(
                this.property,
                MathUtil.doubleToLong(grossCashflow * financialYearRemaining),
                MathUtil.doubleToLong(rentalIncome * financialYearRemaining),
                MathUtil.doubleToLong(taxSavings * financialYearRemaining),
                MathUtil.doubleToLong(youPay * financialYearRemaining),
                MathUtil.doubleToLong(nettCashflow * financialYearRemaining),
                MathUtil.doubleToLong(interest * financialYearRemaining),
                MathUtil.doubleToLong(annualIncomeAfterIP
                        * financialYearRemaining),
                MathUtil.doubleToLong(annualTaxAfterIP * financialYearRemaining),
                MathUtil.doubleToLong(annualIncomeBeforeIP
                        * financialYearRemaining),
                MathUtil.doubleToLong(annualTaxBeforeIP
                        * financialYearRemaining),
                MathUtil.doubleToLong(totalNettIncome * financialYearRemaining),
                MathUtil.doubleToLong(ongoingExpenses * financialYearRemaining));
        return newResult;
    }

    public Long getTaxSavings() {
        return frequencify(taxSavings);
    }

    public Long getGrossCashflow() {
        return frequencify(grossCashflow);
    }

    private Long frequencify(Long aNumber) {
        return (long) (FREQUENCY_FACTORS.get(frequency) * aNumber);
    }

    public Long getRentalIncome() {
        return frequencify(rentalIncome);
    }

    public Long getYouPay() {
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

    public Long getInterest() {
        return frequencify(interest);
    }

    public Long getOngoingExpenses() {
        return frequencify(ongoingExpenses);
    }

    public Long getAnnualIncomeAfterIP() {
        return frequencify(annualIncomeAfterIP);
    }

    public Long getAnnualTaxAfterIP() {
        return frequencify(annualTaxAfterIP);
    }

    public Long getAnnualIncomeBeforeIP() {
        return frequencify(annualIncomeBeforeIP);
    }

    public Long getAnnualTaxBeforeIP() {
        return frequencify(annualTaxBeforeIP);
    }

    public Long getNettCashflow() {
        return frequencify(nettCashflow);
    }

    public Long getTotalNettIncome() {
        return frequencify(totalNettIncome);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public Long getTotalExpense() {
        return getOngoingExpenses() + getInterest();
    }

    public Long getTotalIncome() {
        return getTaxSavings() + getRentalIncome();
    }

    public Long getTotalOutOfPocket() {
        Long outOfPocket = getTotalExpense() - getTotalIncome();
        if (outOfPocket < 0) {
            return 0l;
        }
        return outOfPocket;
    }

    public double getGrossYield() {
        return MathUtil.scaled((double) 100 * getRentalIncome()
                / property.getPurchasePrice());
    }

    public double getNettYield() {
        return MathUtil.scaled((double) 100
                * (getRentalIncome() - getOngoingExpenses())
                / property.getPurchasePrice());
    }

    Property getProperty() {
        return property;
    }

    public void changeFrequency(String frequency) {
        this.frequency = frequency;
    }
}
