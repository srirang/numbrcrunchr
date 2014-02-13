package au.com.numbrcrunchr.domain;

public interface TaxRateRepository {
    TaxRate getRate(String taxYear, double income);

    boolean hasAllData();
}
