package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public class LoanBalanceCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long calculateLoanBalance(Long askingPrice, Long purchaseCosts,
            Long deposit) {
        return askingPrice + purchaseCosts - deposit;
    }

}
