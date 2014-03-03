package au.com.numbrcrunchr.domain;

/**
 * 
 * @author AMIS005
 */
public class InterestCalculator {
    public double calculateInterest(double loanBalance, double interestRate) {
        return loanBalance * interestRate / 100;
    }
}
