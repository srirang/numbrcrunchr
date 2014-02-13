package au.com.numbrcrunchr.domain;

public class ReturnSummary {
    private double cashInvested;
    private double grossYearlyCashflow;
    private double grossYield;
    private double nettYield;
    private double nettYearlyCashflow;
    private String year;

    private ReturnSummary(String year, double cashInvested,
    		double grossYearlyCashflow, double grossYield,
    		double nettYearlyCashflow, double nettYield) {
        super();
        this.year = year;
        this.cashInvested = cashInvested;
        this.grossYearlyCashflow = grossYearlyCashflow;
        this.grossYield = grossYield;
        this.nettYield = nettYield;
        this.nettYearlyCashflow = nettYearlyCashflow;
    }

    public ReturnSummary(FeasibilityAnalysisResult result) {
        this(result.getYear(), result.getProperty().getDeposit(), result
                .getGrossCashflow(), result.getGrossYield(), result
                .getNettCashflow(), result.getNettYield());
    }

    public double getCashInvested() {
        return cashInvested;
    }

    public double getGrossYearlyCashflow() {
        return grossYearlyCashflow;
    }

    public double getGrossYield() {
        return grossYield;
    }

    public double getNettYearlyCashflow() {
        return nettYearlyCashflow;
    }

    public double getNettYield() {
        return nettYield;
    }

    public String getYear() {
        return year;
    }
}
