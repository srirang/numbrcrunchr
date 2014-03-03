package au.com.numbrcrunchr.domain;

public class Repayment {

    private final int period;
    private final double principal;
    private final double interest;
    private final double loanBalance;
    private final double repayment;

    public Repayment(int period, double principal, double interest,
            double loanBalance) {
        super();
        this.period = period;
        this.principal = principal;
        this.interest = interest;
        this.loanBalance = loanBalance;
        this.repayment = this.principal + this.interest;
    }

    public Double getInterest() {
        return interest;
    }

    public Double getLoanBalance() {
        return loanBalance;
    }

    public int getPeriod() {
        return period;
    }

    public Double getPrincipal() {
        return principal;
    }

    public Double getRepayment() {
        return repayment;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        return sb.append("Month ").append(getPeriod()).append("\t")
                .append(getLoanBalance()).append("\t").append(getRepayment())
                .append("\t").append(getInterest()).append("\t")
                .append(getPrincipal()).append("\n").toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (object.getClass() != getClass()) {
            return false;
        }
        Repayment repayment = (Repayment) object;
        return getPrincipal().equals(repayment.getPrincipal())
                && getInterest().equals(repayment.getInterest())
                && getRepayment().equals(repayment.getRepayment())
                && getLoanBalance().equals(repayment.getLoanBalance());
    }

    protected double principal() {
        return this.principal;
    }

    protected double interest() {
        return this.interest;
    }

    protected double loanBalance() {
        return this.loanBalance;
    }

    protected double repayment() {
        return this.repayment;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }

}
