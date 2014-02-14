package au.com.numbrcrunchr.domain;

public class LVRCalculator {
    public double calculateLvr(double loanAmount, double propertyValue) {
        if (propertyValue == 0) {
            return 0;
        }
        return (loanAmount / propertyValue) * 100;
    }

    public double calculateLoanAmount(double propertyValue, double lvr) {
        if (propertyValue == 0) {
            return 0;
        }
        return lvr * propertyValue;
    }
}
