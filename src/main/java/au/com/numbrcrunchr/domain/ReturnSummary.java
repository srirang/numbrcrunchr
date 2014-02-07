package au.com.numbrcrunchr.domain;

public class ReturnSummary {
	private long cashInvested;
	private long grossYearlyCashflow;
	private double grossYield;
	private double nettYield;
	private long nettYearlyCashflow;
	private String year;

	private ReturnSummary(String year, long cashInvested, long grossYearlyCashflow,
			double grossYield, long nettYearlyCashflow, double nettYield) {
		super();
		this.year = year;
		this.cashInvested = cashInvested;
		this.grossYearlyCashflow = grossYearlyCashflow;
		this.grossYield = grossYield;
		this.nettYield = nettYield;
		this.nettYearlyCashflow = nettYearlyCashflow;
	}

	public ReturnSummary(FeasibilityAnalysisResult result) {
		this(result.getYear(), result.getProperty().getDeposit(), result.getGrossCashflow(),
				result.getGrossYield(), result.getNettCashflow(), result
						.getNettYield());
	}

	public long getCashInvested() {
		return cashInvested;
	}

	public long getGrossYearlyCashflow() {
		return grossYearlyCashflow;
	}

	public double getGrossYield() {
		return grossYield;
	}

	public long getNettYearlyCashflow() {
		return nettYearlyCashflow;
	}

	public double getNettYield() {
		return nettYield;
	}

	public String getYear() {
		return year;
	}
}
