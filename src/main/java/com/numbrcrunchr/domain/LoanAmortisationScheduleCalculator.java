package com.numbrcrunchr.domain;

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
    private List<Repayment> calculatePrincipalAndInterestAmortisationSchedule(
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
        Repayment repayment = new Repayment(i + 1, principal, interest,
                loanAmount);
        List<Repayment> repayments = new ArrayList<Repayment>(termInMonths);
        i++;
        repayments.add(repayment);
        while (loanBalance > 0) {
            loanBalance = repayments.get(i - 1).getLoanBalance().doubleValue()
                    - repayments.get(i - 1).getPrincipal().doubleValue();
            interest = interestCalculator.calculateInterest(loanBalance,
                    interestRate) / 12;
            principal = monthlyRepayment - interest;
            repayments.add(repayment = new Repayment(i + 1, principal,
                    interest, loanBalance));
            i++;
        }
        return repayments;
    }

    public void setInterestCalculator(
            InterestOnlyLoanRepaymentCalculator interestCalculator) {
        this.interestCalculator = interestCalculator;
    }

    public AmortisationSchedule calculateAmortisationSchedule(
            double loanAmount, int interestOnlyPeriod, int termInYears,
            double interestRate) {
        List<Repayment> interestOnlyAmortisations = new ArrayList<Repayment>(
                interestOnlyPeriod);
        double loanBalance = loanAmount;
        double interest = interestCalculator.calculateInterest(loanBalance,
                interestRate) / 12;

        if (interestOnlyPeriod > 0) {
            for (int i = 0; i < interestOnlyPeriod * 12; i++) {
                interestOnlyAmortisations.add(new Repayment(i + 1, 0, interest,
                        loanBalance));
            }
        }

        List<Repayment> princpalAndInterestAmortisations = this
                .calculatePrincipalAndInterestAmortisationSchedule(loanAmount,
                        termInYears - interestOnlyPeriod, interestRate);
        List<Repayment> amortisations = new ArrayList<Repayment>(
                interestOnlyAmortisations);
        amortisations.addAll(princpalAndInterestAmortisations);

        return new AmortisationSchedule(loanAmount, interestRate, termInYears,
                interestOnlyAmortisations, amortisations);
    }

    public AmortisationSchedule calculateAmortisationSchedule(
            double loanAmount, int termInYears, double interestRate) {
        return this.calculateAmortisationSchedule(loanAmount, 0, termInYears,
                interestRate);
    }
}
