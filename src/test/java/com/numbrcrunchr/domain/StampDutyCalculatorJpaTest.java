package com.numbrcrunchr.domain;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class StampDutyCalculatorJpaTest extends
        StampDutyCalculatorAbstractTestCase {
    @Autowired
    private StampDutyCalculator stampDutyCalculator;

    @Override
    StampDutyCalculator getStampDutyCalculator() {
        return this.stampDutyCalculator;
    }
}
