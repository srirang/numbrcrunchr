package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoanAmortisationScheduleCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    private InterestOnlyLoanRepaymentCalculator interestCalculator;

    /**
     * Formulae from: http://www.amortizationer.com/amortization-formula.html
     * Let P be the monthly payment amount you have to pay Let N be the number
     * of monthly payments (installments) Let I be the annual interest rate Let
     * A be the amount borrowed Let R = (1 + I/12)
     * 
     * Then the loan amortization formula for monthly payment (P) is: P = A * (
     * R - 1 ) / ( 1 - R^-N )
     * 
     * @param loanBalance
     *            (A)
     * @param termInYears
     *            (N / 12)
     * @param interestRate
     *            (I)
     * @return
     */
    private List<Amortisation> calculatePrincipalAndInterestAmortisationSchedule(
            double loanAmount, int termInYears, double interestRate) {
        double loanBalance = loanAmount;
        double r = 1 + (interestRate / 100) / 12;
        int termInMonths = termInYears * 12;
        double monthlyRepayment = loanBalance
                * ((r - 1) / (1 - 1 / Math.pow(r, termInMonths)));
        double interest = interestCalculator.calculateInterest(loanBalance,
                interestRate) / 12;
        double principal = monthlyRepayment - interest;

        int i = 0;
        Amortisation amortisation = new Amortisation(i + 1, principal,
                interest, loanAmount);
        List<Amortisation> amortisations = new ArrayList<Amortisation>(
                termInMonths);
        i++;
        amortisations.add(amortisation);
        loanBalance = amortisations.get(i - 1).getLoanBalance().doubleValue()
                - amortisations.get(i - 1).getPrincipal().doubleValue();
        while (loanBalance > 0) {
            // loanBalance = amortisations.get(i - 1).getLoanBalance()
            // .doubleValue()
            // - amortisations.get(i - 1).getPrincipal().doubleValue();
            interest = interestCalculator.calculateInterest(loanBalance,
                    interestRate) / 12;
            principal = monthlyRepayment - interest;
            amortisations.add(amortisation = new Amortisation(i + 1, principal,
                    interest, loanBalance));
            i++;
            loanBalance = amortisations.get(i - 1).getLoanBalance()
                    .doubleValue()
                    - amortisations.get(i - 1).getPrincipal().doubleValue();
        }
        return amortisations;
    }

    public void setInterestCalculator(
            InterestOnlyLoanRepaymentCalculator interestCalculator) {
        this.interestCalculator = interestCalculator;
    }

    public AmortisationSchedule calculateAmortisationSchedule(
            double loanAmount, int interestOnlyPeriod, int termInYears,
            double interestRate) {
        List<Amortisation> interestOnlyAmortisations = new ArrayList<Amortisation>(
                interestOnlyPeriod);
        double loanBalance = loanAmount;
        double interest = interestCalculator.calculateInterest(loanBalance,
                interestRate) / 12;

        if (interestOnlyPeriod > 0) {
            for (int i = 0; i < interestOnlyPeriod * 12; i++) {
                interestOnlyAmortisations.add(new Amortisation(i + 1, 0,
                        interest, loanBalance));
            }
        }

        List<Amortisation> princpalAndInterestAmortisations = this
                .calculatePrincipalAndInterestAmortisationSchedule(loanAmount,
                        termInYears - interestOnlyPeriod, interestRate);
        List<Amortisation> amortisations = new ArrayList<Amortisation>(
                interestOnlyAmortisations);
        amortisations.addAll(princpalAndInterestAmortisations);

        double baloonPrincipal = princpalAndInterestAmortisations.get(
                princpalAndInterestAmortisations.size() - 1).getPrincipal();
        double baloonInterest = interestCalculator.calculateInterest(
                baloonPrincipal, interestRate);
        int baloonPeriod = princpalAndInterestAmortisations.get(
                princpalAndInterestAmortisations.size() - 1).getPeriod() + 1;
        double baloonBalance = princpalAndInterestAmortisations.get(
                princpalAndInterestAmortisations.size() - 1).getLoanBalance()
                - baloonPrincipal;
        Amortisation baloonRepayment = new Amortisation(baloonPeriod,
                baloonInterest, baloonPrincipal, baloonBalance);
        return AmortisationSchedule.createAmortisationSchedule(
                interestOnlyAmortisations, amortisations, baloonRepayment);
    }

    public AmortisationSchedule calculateAmortisationSchedule(
            double loanAmount, int termInYears, double interestRate) {
        return this.calculateAmortisationSchedule(loanAmount, 0, termInYears,
                interestRate);
    }
}
