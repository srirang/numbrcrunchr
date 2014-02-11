package au.com.numbrcrunchr.domain;

import java.io.Serializable;

/**
 * 
 * @author AMIS005
 */
public class InterestOnlyLoanRepaymentCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public double calculateInterest(double loanBalance, double interestRate) {
        double yearlyInterest = loanBalance * interestRate / 100;
        return yearlyInterest;
    }
}
