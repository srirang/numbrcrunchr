package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class InterestOnlyLoanRepaymentCalculatorTest {

    @Autowired
    private InterestOnlyLoanRepaymentCalculator calculator;

    @Test
    public void checkInterest() {
        assertEquals(1852.50, calculator.calculateInterest(300000, 7.41) / 12,
                0);
    }
}
