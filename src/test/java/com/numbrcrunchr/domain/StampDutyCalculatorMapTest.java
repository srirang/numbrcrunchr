package com.numbrcrunchr.domain;

public class StampDutyCalculatorMapTest extends
        StampDutyCalculatorAbstractTestCase {

    @Override
    StampDutyCalculator getStampDutyCalculator() {
        return new StampDutyCalculator(new StampDutyRepositoryMapImpl());
    }
}
