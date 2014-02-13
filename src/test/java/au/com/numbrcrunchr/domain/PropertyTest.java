package au.com.numbrcrunchr.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class PropertyTest {
    @Autowired
    private ProjectionParameters projectionParameters;

    private static final Logger LOGGER = Logger.getLogger(PropertyTest.class
            .getName());

    @Test
    public void checkSum() {
        assertEquals(
                55,
                new Property().sum(new Long[] { 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l,
                        9l, 10l }), 0);
    }

    @Test
    public void checkOwnersList() {
        assertNotNull(new Property().getOwnerList());
        assertEquals(0, new Property().getOwnerList().size());
    }

    @Test
    public void checkGrossIncome() {
        Property property = new Property();
        List<Owner> owners = new ArrayList<Owner>();
        property.setOwnerList(owners);
        for (int i = 0; i < 10; i++) {
            Owner o = new Owner();
            o.setAnnualIncome(i * 100000l);
            o.setTax(i * 100l);
            owners.add(o);
        }
        assertEquals(4500000, property.calculateOwnerGrossIncome());
        assertEquals(4500, property.calculateOwnerTax());
    }

    @Test
    public void checkClone() throws CloneNotSupportedException {
        Property property = createAProperty();

        LOGGER.info(new Date().toString());
        Property copy = (Property) property.clone();
        LOGGER.info(new Date().toString());

        assertNotSame(property, copy);
        assertEquals(property, copy);
    }

    @Test
    public void checkThousandClones() throws CloneNotSupportedException {
        Property property = createAProperty();

        LOGGER.info(new Date().toString());
        for (int i = 0; i < 1001; i++) {
            property.clone();
        }
        LOGGER.info(new Date().toString());
    }

    @Test
    public void checkProjection() throws CloneNotSupportedException {
        Property property = createAProperty();
        property.projectBy(projectionParameters);
        assertEquals(100000 * 1.035, property.calculateOwnerGrossIncome(),
                0.0001);
        assertEquals(260, property.getWeeklyRent(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(1300, property.getPropertyManagementFees(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(550000 * 1.055, property.getMarketValue(),
                MathUtilTest.ROUNDING_ERROR_TOLERANCE);
        assertEquals(550000, property.getTotalPurchaseCost(), 0);
    }

    private Property createAProperty() {
        Property property = PropertyTest.createProperty(100000l, true, 500000l,
                10000l, Byte.valueOf("50"), 250l, 7d, 10d);
        property.setPurchasePrice(550000l);
        property.setBuildingInspectionFees(500l);
        property.setBuildingValue(340000l);
        property.setCleaning(500l);
        property.setConstructionDate(new Date());
        property.setCouncilRates(2000l);
        property.setDeposit(5000l);
        property.setFittingsValue(20000l);
        property.setGardening(250l);
        property.setLandlordsInsurance(500l);
        property.setLegalFees(200l);
        property.setLoanApplicationFees(200l);
        property.setMaintenance(500l);
        property.setMiscOngoingExpenses(100l);
        property.setMortgageInsurance(6000l);
        property.setMortgageInsuranceStampDuty(600l);
        property.setMortgageStampDuty(50l);
        property.setAddress("my address");
        property.setPurchaseDate(new Date());
        property.setStampDuty(30000l);
        property.setState(State.VIC);
        property.setStrata(1000l);
        property.setTaxExpenses(250l);
        property.setTitleRegistrationFees(400l);
        property.initialisePurhcaseCostAndMarketValue(550000l);
        property.setWaterCharges(800l);
        return property;
    }

    @Test
    public void checkPropertyManagementFees() {
        Property property = createAProperty();
        assertNotNull(property.getManagementFeeRate());
        assertEquals(250, property.getWeeklyRent(), 0);
        assertEquals(1250, property.getPropertyManagementFees(), 0);
    }

    public static Property createProperty(long annualIncome,
            boolean medicareLevyApplies, long loanAmount, long ongoingCosts,
            byte weeksRented, long weeklyRent, double interestRate,
            double managementFeeRate) {
        Property property = new Property();
        property.setPurchasePrice(loanAmount);
        property.setLoanAmount(loanAmount);
        property.setInterestRate(interestRate);
        property.setWeeklyRent(weeklyRent);
        property.setWeeksRented(weeksRented);
        property.setCouncilRates(ongoingCosts);
        property.setManagementFeeRate(managementFeeRate);
        Owner owner = new Owner();
        owner.setAnnualIncome(annualIncome);
        owner.setMedicareLevyApplies(medicareLevyApplies);
        property.addOwner(owner);
        return property;
    }

    public static Property createProperty(long annualIncome,
            boolean medicareLevyApplies, long loanAmount,
            OngoingCosts ongoingCosts, byte weeksRented, long weeklyRent,
            double interestRate, double managementFeeRate) {
        Property property = new Property();
        property.setPurchasePrice(loanAmount);
        property.setLoanAmount(loanAmount);
        property.setInterestRate(interestRate);
        property.setWeeklyRent(weeklyRent);
        property.setWeeksRented(weeksRented);
        property.setOngoingCosts(ongoingCosts);
        property.setManagementFeeRate(managementFeeRate);
        Owner owner = new Owner();
        owner.setAnnualIncome(annualIncome);
        owner.setMedicareLevyApplies(medicareLevyApplies);
        property.addOwner(owner);
        return property;
    }
}
