package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public interface TaxRateRepository extends Serializable {
    TaxRate getRate(String taxYear, double income);

    boolean hasAllData();
}
