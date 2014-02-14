package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class AmortisationScheduleTest {
    @Autowired
    private LoanAmortisationScheduleCalculator calculator;

    @Test
    public void checkMonthly() {
        double loan = 300000;
        double interest = 7.41;
        int termInYears = 30;
        AmortisationSchedule amortisationSchedule = calculator
                .calculateAmortisationSchedule(loan, termInYears, interest);
        assertEquals(30, amortisationSchedule.getYearlyAmortisations().size());
    }
}
