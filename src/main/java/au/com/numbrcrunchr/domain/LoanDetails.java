package au.com.numbrcrunchr.domain;

public class LoanDetails {
    private long totalCost;
    private long deposit;
    private double lvr;
    private long loanAmount;

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setLvr(double lvr) {
        this.lvr = lvr;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public long getDeposit() {
        return deposit;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public double getLvr() {
        return lvr;
    }

    public long getTotalCost() {
        return totalCost;
    }
}
