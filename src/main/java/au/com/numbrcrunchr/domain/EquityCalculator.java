package au.com.numbrcrunchr.domain;

// TODO Create test for EquityCalculator
public class EquityCalculator {
	public double calculateEquity(double marketValue, double loanBalance) {
		double equity = (marketValue * 80 / 100) - loanBalance;
		return equity < 0 ? 0 : equity;
	}
}
