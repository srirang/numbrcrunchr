package au.com.numbrcrunchr.domain;

/**
 * 
 * @author AMIS005
 */
public class InterestCalculator {
    public double calculateInterest(double loanBalance, double interestRate) {
        double yearlyInterest = loanBalance * interestRate / 100;
        return yearlyInterest;
    }
}
