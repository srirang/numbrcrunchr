package au.com.numbrcrunchr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AmortisationSchedule {

    private List<Amortisation> monthlyAmortisations = new ArrayList<Amortisation>();
    private List<Amortisation> yearlyAmortisations = new ArrayList<Amortisation>();

    private AmortisationSchedule() {
    }

    public static AmortisationSchedule createAmortisationSchedule(
            List<Amortisation> monthlyAmortisations) {
        AmortisationSchedule schedule = new AmortisationSchedule();
        schedule.monthlyAmortisations = monthlyAmortisations;
        schedule.updateYearlyAmortisations();
        return schedule;
    }

    public List<Amortisation> getMonthlyAmortisations() {
        return Collections.unmodifiableList(monthlyAmortisations);
    }

    public List<Amortisation> getYearlyAmortisations() {
        return Collections.unmodifiableList(yearlyAmortisations);
    }

    public void updateYearlyAmortisations() {
        int i = 1;
        this.yearlyAmortisations = new ArrayList<Amortisation>();
        double interest = 0;
        double loanBalance = 0;
        double principal = 0;
        int year = 1;
        for (Amortisation monthlyAmortisation : monthlyAmortisations) {
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
                this.yearlyAmortisations.add(yearly);
                i++;
                continue;
            }
            i++;
        }
    }
}
