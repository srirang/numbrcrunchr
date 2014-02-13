package au.com.numbrcrunchr.domain;

// TODO Create test for EquityCalculator
public class EquityCalculator {
	public long calculateEquity(long marketValue, long loanBalance) {
		long equity = (marketValue * 80 / 100) - loanBalance;
		return equity < 0 ? 0 : equity;
	}
}
