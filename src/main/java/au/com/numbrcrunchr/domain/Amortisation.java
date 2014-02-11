package au.com.numbrcrunchr.domain;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Amortisation {

    private final int period;
    private final double principal;
    private final double interest;
    private final double loanBalance;
    private final double repayment;

    public Amortisation(int period, double principal, double interest,
            double loanBalance) {
        super();
        this.period = period;
        this.principal = principal;
        this.interest = interest;
        this.loanBalance = loanBalance;
        this.repayment = this.principal + this.interest;
    }

    public BigDecimal getInterest() {
        return MathUtil.doubleToBigDecimal(interest);
    }

    public BigDecimal getLoanBalance() {
        return MathUtil.doubleToBigDecimal(loanBalance);
    }

    public int getPeriod() {
        return period;
    }

    public BigDecimal getPrincipal() {
        return MathUtil.doubleToBigDecimal(principal);
    }

    public BigDecimal getRepayment() {
        return MathUtil.doubleToBigDecimal(repayment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("period", period)
                .append("principal", getPrincipal())
                .append("interest", getInterest())
                .append("loanBalance", getLoanBalance())
                .append("repayment", getRepayment()).toString();
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
        Amortisation amortisation = (Amortisation) object;
        return (getPrincipal().equals(amortisation.getPrincipal())
        // && getPeriod() == amortisation.getPeriod()
        && getInterest().equals(amortisation.getInterest()))
                && getRepayment().equals(amortisation.getRepayment())
                && getLoanBalance().equals(amortisation.getLoanBalance());
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
