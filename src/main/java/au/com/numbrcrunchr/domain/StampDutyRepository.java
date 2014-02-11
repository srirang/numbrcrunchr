package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public interface StampDutyRepository extends Serializable {
    StampDutyRate getRate(String state, long value);

    boolean hasAllData();
}
