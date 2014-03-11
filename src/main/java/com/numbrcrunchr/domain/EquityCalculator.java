package com.numbrcrunchr.domain;

import java.io.Serializable;

public class EquityCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    public double calculateEquity(double marketValue, double loanBalance) {
        double equity = marketValue * 80 / 100 - loanBalance;
        return equity < 0 ? 0 : equity;
    }
}
