package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EquityCalculatorTest {

	@Test
	public void checkNegativeEquity() {
		assertEquals(0, new EquityCalculator().calculateEquity(300000, 400000));
	}

	@Test
	public void checkEquity() {
		assertEquals(20000, new EquityCalculator().calculateEquity(400000, 300000));
	}
}
