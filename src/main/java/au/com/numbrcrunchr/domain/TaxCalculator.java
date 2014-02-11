package au.com.numbrcrunchr.domain;

import java.util.Date;
import java.util.logging.Logger;

public class TaxCalculator {

    private static final Logger LOGGER = Logger.getLogger(TaxCalculator.class
            .getName());
    private TaxRateRepository taxRateRepository;
    private static final Double MEDICARE_LEVY_SURCHARGE_RATE = new Double(
            "0.015");

    public TaxCalculator() {
    }

    public Long calculateTax(Date taxYear, long grossIncome,
            boolean includeMedicareLevySurcharge) {
        String taxYearString = FinancialYearUtils.getTaxYear(taxYear);
        TaxRate rate = taxRateRepository.getRate(taxYearString, grossIncome);
        LOGGER.info(rate.toString());
        long flatAmount = rate.getFlatRate().longValue();
        double taxOnIncome = flatAmount
                + ((grossIncome - rate.getLowerLimit().longValue()) * rate
                        .getPercentage().doubleValue());
        double medicareLevy = 0;
        if (includeMedicareLevySurcharge) {
            medicareLevy = grossIncome
                    * MEDICARE_LEVY_SURCHARGE_RATE.doubleValue();
        }
        double totalTax = taxOnIncome + medicareLevy;

        return MathUtil.doubleToLong(totalTax);
    }

    public Long calculateNettIncome(Date taxYear, long grossIncome,
            boolean includeMedicareLevySurcharge) {
        long tax = calculateTax(taxYear, grossIncome,
                includeMedicareLevySurcharge);
        long nettIncome = grossIncome - tax;
        return nettIncome;
    }

    public void setTaxRateRepository(TaxRateRepository taxRateRepository) {
        this.taxRateRepository = taxRateRepository;
    }
}
