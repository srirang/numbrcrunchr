package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public class LoanBalanceCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public double calculateLoanBalance(double askingPrice,
            double purchaseCosts, double deposit) {
        return askingPrice + purchaseCosts - deposit;
    }

}
