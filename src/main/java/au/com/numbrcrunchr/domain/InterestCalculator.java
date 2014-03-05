package au.com.numbrcrunchr.domain;

import java.io.Serializable;

/**
 * 
 * @author AMIS005
 */
public class InterestCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public double calculateInterest(double loanBalance, double interestRate) {
        return loanBalance * interestRate / 100;
    }
}
