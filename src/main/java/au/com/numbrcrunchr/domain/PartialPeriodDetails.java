package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.Date;

public class PartialPeriodDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private final double proRataFinancialYear;
    private final Date financialYearEnd;

    public PartialPeriodDetails(double proRataFinancialYear,
            Date financialYearEnd) {
        super();
        this.proRataFinancialYear = proRataFinancialYear;
        this.financialYearEnd = financialYearEnd;
    }

    public double getProRataFinancialYear() {
        return proRataFinancialYear;
    }

    public Date getFinancialYearEnd() {
        return financialYearEnd;
    }

    public long getDaysInFirstFinancialYear() {
        return MathUtil.doubleToLong(proRataFinancialYear * 326);
    }
}
