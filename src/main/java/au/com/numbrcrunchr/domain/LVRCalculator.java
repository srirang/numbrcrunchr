package au.com.numbrcrunchr.domain;

public class LVRCalculator {
    public double calculateLvr(Long loanAmount, long propertyValue) {
        if (propertyValue == 0) {
            return 0;
        }
        return ((double) loanAmount / (double) propertyValue) * 100;
    }

    public long calculateLoanAmount(Long propertyValue, double lvr) {
        if (propertyValue == 0) {
            return 0;
        }
        return MathUtil.doubleToLong(lvr * propertyValue);
    }
}
