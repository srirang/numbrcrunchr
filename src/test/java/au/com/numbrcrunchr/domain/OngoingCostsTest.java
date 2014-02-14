package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class OngoingCostsTest {
    @Test
    public void testABrandNewOngoingCosts() {
        OngoingCosts ongoingCosts = new OngoingCosts();
        assertNotNull(ongoingCosts.getCleaning());
        assertNotNull(ongoingCosts.getCouncilRates());
        assertNotNull(ongoingCosts.getGardening());
        assertNotNull(ongoingCosts.getLandlordsInsurance());
        assertNotNull(ongoingCosts.getMaintenance());
        assertNotNull(ongoingCosts.getMiscOngoingExpenses());
        assertNotNull(ongoingCosts.getStrata());
        assertNotNull(ongoingCosts.getTaxExpenses());
        assertNotNull(ongoingCosts.getTotalOngoingCosts(100l));
        assertNotNull(ongoingCosts.getWaterCharges());
    }
}
