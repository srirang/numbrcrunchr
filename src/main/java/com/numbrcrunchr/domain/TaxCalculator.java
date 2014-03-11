package com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.Date;

public class TaxCalculator implements Serializable {
    private static final long serialVersionUID = 1L;

    private TaxRateRepository taxRateRepository;
    private static final Double MEDICARE_LEVY_SURCHARGE_RATE = new Double(
            "0.015");

    public double calculateTax(Date taxYear, double grossIncome,
            boolean includeMedicareLevySurcharge) {
        String taxYearString = FinancialYearUtils.getTaxYear(taxYear);
        TaxRate rate = taxRateRepository.getRate(taxYearString, grossIncome);
        double taxOnIncome = rate.getFlatRate()
                + (grossIncome - rate.getLowerLimit()) * rate.getPercentage();
        double medicareLevy = 0;
        if (includeMedicareLevySurcharge) {
            medicareLevy = grossIncome
                    * MEDICARE_LEVY_SURCHARGE_RATE.doubleValue();
        }
        double totalTax = taxOnIncome + medicareLevy;

        return MathUtil.doubleToLong(totalTax);
    }

    public double calculateNettIncome(Date taxYear, double grossIncome,
            boolean includeMedicareLevySurcharge) {
        double tax = calculateTax(taxYear, grossIncome,
                includeMedicareLevySurcharge);
        return grossIncome - tax;
    }

    public void setTaxRateRepository(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }
}
