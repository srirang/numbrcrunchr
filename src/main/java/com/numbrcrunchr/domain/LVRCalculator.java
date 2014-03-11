package com.numbrcrunchr.domain;

import java.io.Serializable;

public class LVRCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

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
