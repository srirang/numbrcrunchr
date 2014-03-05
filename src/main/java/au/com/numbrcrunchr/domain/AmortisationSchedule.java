package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AmortisationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Repayment> interestOnlyRepayments = new ArrayList<Repayment>();
    private List<Repayment> monthlyRepayments = new ArrayList<Repayment>();
    private List<Repayment> yearlyRepayments = new ArrayList<Repayment>();
    private final double loanAmount;
    private final double interestRate;
    private final double termInYears;

    public AmortisationSchedule(double loanAmount, double interestRate,
            double termInYears, List<Repayment> interestOnlyRepayments,
            List<Repayment> monthlyRepayments) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.termInYears = termInYears;
        this.monthlyRepayments = monthlyRepayments;
        this.interestOnlyRepayments = interestOnlyRepayments;
        this.updateYearlyAmortisations();
    }

    public List<Repayment> getMonthlyRepayments() {
        return Collections.unmodifiableList(monthlyRepayments);
    }

    public List<Repayment> getInterestOnlyRepayments() {
        return Collections.unmodifiableList(interestOnlyRepayments);
    }

    public List<Repayment> getYearlyRepayments() {
        return Collections.unmodifiableList(yearlyRepayments);
    }

    public void updateYearlyAmortisations() {
        int i = 1;
        this.yearlyRepayments = new ArrayList<Repayment>();
        double interest = 0;
        double loanBalance = 0;
        double principal = 0;
        int year = 1;
        for (Repayment monthlyAmortisation : monthlyRepayments) {
            interest += monthlyAmortisation.interest();
            principal += monthlyAmortisation.principal();
            if (i % 12 == 0) {
                loanBalance = monthlyAmortisation.loanBalance();
                Repayment yearly = new Repayment(year, principal, interest,
                        loanBalance);
                interest = 0;
                loanBalance = 0;
                principal = 0;
                year++;
                this.yearlyRepayments.add(yearly);
                i++;
                continue;
            }
            i++;
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Amortisation Schedule:");
        sb.append("\n\n\n\n---------------------------------------------------------------------------");
        sb.append("\nLoan Amount:").append("\t").append(this.getLoanAmount());
        sb.append("\nLoan Term:").append("\t").append(this.getTermInYears())
                .append(" Years");
        sb.append("\nInterest Rate:").append("\t")
                .append(this.getInterestRate()).append("%");

        sb.append("\nMonth\tBalance\tRepayment\tInterest\tPricipal\n");
        for (Repayment result : this.monthlyRepayments) {
            sb.append(result.toString());
        }
        sb.append("\n---------------------------------------------------------------------------\n\n\n\n");
        return sb.toString();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getTermInYears() {
        return termInYears;
    }
}
