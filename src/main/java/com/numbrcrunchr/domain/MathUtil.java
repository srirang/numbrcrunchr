package com.numbrcrunchr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public final class MathUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private MathUtil() {

    }

    public static double scaled(double number) {
        return new BigDecimal(String.valueOf(number)).setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Long bigDecimalToLong(BigDecimal value) {
        return value.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public static Long doubleToLong(double value) {
        return bigDecimalToLong(new BigDecimal(String.valueOf(value)));
    }

    public static double increaseBy(double value, double rate) {
        return value * (rate / 100 + 1);
    }

}
