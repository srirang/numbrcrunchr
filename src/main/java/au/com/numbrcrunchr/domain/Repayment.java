package au.com.numbrcrunchr.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        return sb.append("Month ").append(getPeriod()).append("\t")
                .append(getLoanBalance()).append("\t").append(repayment)
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
        Repayment rhs = (Repayment) object;
        return new EqualsBuilder().append(this.loanBalance, rhs.loanBalance)
                .append(this.interest, rhs.interest)
                .append(this.principal, rhs.principal)
                .append(this.repayment, rhs.repayment).isEquals();
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
        throw new UnsupportedOperationException(
                "Hascode is not supported for objects of this type");
    }

}
