package au.com.numbrcrunchr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AmortisationSchedule {

    private List<Amortisation> interestOnlyRepayments = new ArrayList<Amortisation>();
    private List<Amortisation> monthlyRepayments = new ArrayList<Amortisation>();
    private List<Amortisation> yearlyRepayments = new ArrayList<Amortisation>();
    private Amortisation baloonPayment;

    private AmortisationSchedule() {
    }

    public static AmortisationSchedule createAmortisationSchedule(
            List<Amortisation> interestOnlyRepayments,
            List<Amortisation> monthlyRepayments, Amortisation baloonPayment) {
        AmortisationSchedule schedule = new AmortisationSchedule();
        schedule.monthlyRepayments = monthlyRepayments;
        schedule.interestOnlyRepayments = interestOnlyRepayments;
        schedule.updateYearlyAmortisations();
        schedule.baloonPayment = baloonPayment;
        return schedule;
    }

    public List<Amortisation> getMonthlyRepayments() {
        return Collections.unmodifiableList(monthlyRepayments);
    }

    public List<Amortisation> getInterestOnlyRepayments() {
        return Collections.unmodifiableList(interestOnlyRepayments);
    }

    public List<Amortisation> getYearlyRepayments() {
        return Collections.unmodifiableList(yearlyRepayments);
    }

    public void updateYearlyAmortisations() {
        int i = 1;
        this.yearlyRepayments = new ArrayList<Amortisation>();
        double interest = 0;
        double loanBalance = 0;
        double principal = 0;
        int year = 1;
        for (Amortisation monthlyAmortisation : monthlyRepayments) {
            interest += monthlyAmortisation.interest();
            principal += monthlyAmortisation.principal();
            if (i % 12 == 0) {
                loanBalance = monthlyAmortisation.loanBalance();
                Amortisation yearly = new Amortisation(year, principal,
                        interest, loanBalance);
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

    public Amortisation getBaloonPayment() {
        return baloonPayment;
    }
}
