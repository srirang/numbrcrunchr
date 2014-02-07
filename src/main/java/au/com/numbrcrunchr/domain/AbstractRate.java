package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractRate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "lower_limit")
    private Long lowerLimit;
    @Column(name = "upper_limit")
    private Long upperLimit;
    @Column(name = "flat_rate", precision = 22)
    private BigDecimal flatRate;
    @Column(name = "percentage", precision = 22)
    private BigDecimal percentage;

    public AbstractRate() {
    }

    public AbstractRate(Long lowerLimit, Long upperLimit, BigDecimal flatRate, BigDecimal percentage) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.flatRate = flatRate;
        this.percentage = percentage;
    }

    public AbstractRate(long lowerLimit, long upperLimit, double flatRate, double percentage) {
        this(lowerLimit, upperLimit, new BigDecimal(String.valueOf(flatRate)), new BigDecimal(String.valueOf(percentage)));
    }

    public AbstractRate(long lowerLimit, Long upperLimit, double flatRate, double percentage) {
        this(lowerLimit, upperLimit, new BigDecimal(String.valueOf(flatRate)), new BigDecimal(String.valueOf(percentage)));
    }

    public Long getLowerLimit() {
        return lowerLimit;
    }

    public Long getUpperLimit() {
        return upperLimit;
    }

    public BigDecimal getFlatRate() {
        return flatRate;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public boolean isInRange(Long value) {
        if (lowerLimit == null) {
            if (value.compareTo(upperLimit) <= 0) {
                return true;
            }
        }
        if (upperLimit == null) {
            if (value.compareTo(lowerLimit) >= 0) {
                return true;
            }
        }
        if (lowerLimit == null || upperLimit == null) {
            return false;
        }
        if (value.compareTo(lowerLimit) < 0 || value.compareTo(upperLimit) > 0) {
            return false;
        }
        return true;
    }

    public static String nullSafeNumber(Long number) {
        return number == null ? "null" : number.toString();
    }

    public static String nullSafeNumber(BigDecimal number) {
        return number == null ? "null" : number.toString();
    }

    @Override
    public String toString() {
        return nullSafeNumber(getLowerLimit()) + " < "
                + nullSafeNumber(getUpperLimit()) + " = "
                + nullSafeNumber(flatRate) + " + " + nullSafeNumber(percentage);
    }
}
