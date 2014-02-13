package au.com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class OngoingCosts implements Serializable {
	private static final long serialVersionUID = 1L;
	// TODO: Create test for OngoingCosts
	private static final Long DEFAULT_LANDLORD_INSURANCE = 400l;
	private static final Long DEFAULT_MAINTENANCE = 100l;
	private static final Long DEFAULT_WATER_CHARGES = 800l;
	private static final Long DEFAULT_CLEANING = 100l;
	private static final Long DEFAULT_COUNCIL_RATES = 1500l;
	private static final Long DEFAULT_GARDENING = 100l;
	private static final Long DEFAULT_TAX_EXPENSES = 100l;
	private static final Long DEFAULT_MISC_ONGOING_EXPENSES = 0l;
	private static final Long DEFAULT_STRATA = 0l;

	// Ongoing Costs
	@Column(name = "landlords_insurance")
	private Long landlordsInsurance = DEFAULT_LANDLORD_INSURANCE;

	@Column(name = "maintenance")
	private Long maintenance = DEFAULT_MAINTENANCE;

	@Column(name = "strata")
	private Long strata = DEFAULT_STRATA;

	@Column(name = "water_charges")
	private Long waterCharges = DEFAULT_WATER_CHARGES;

	@Column(name = "cleaning")
	private Long cleaning = DEFAULT_CLEANING;

	@Column(name = "council_rates")
	private Long councilRates = DEFAULT_COUNCIL_RATES;

	@Column(name = "gardening")
	private Long gardening = DEFAULT_GARDENING;

	@Column(name = "tax_expenses")
	private Long taxExpenses = DEFAULT_TAX_EXPENSES;

	@Column(name = "misc_ongoing_expenses")
	private Long miscOngoingExpenses = DEFAULT_MISC_ONGOING_EXPENSES;

	public Long getLandlordsInsurance() {
		return this.landlordsInsurance;
	}

	public void setLandlordsInsurance(Long landlordsInsurance) {
		this.landlordsInsurance = landlordsInsurance;
	}

	public void setMaintenance(Long maintenance) {
		this.maintenance = maintenance;
	}

	public Long getMaintenance() {
		return this.maintenance;
	}

	public void setMiscOngoingExpenses(Long miscOngoingExpenses) {
		this.miscOngoingExpenses = miscOngoingExpenses;
	}

	public Long getMiscOngoingExpenses() {
		return this.miscOngoingExpenses;
	}

	public void setStrata(Long strata) {
		this.strata = strata;
	}

	public Long getStrata() {
		return this.strata;
	}

	public void setWaterCharges(Long waterCharges) {
		this.waterCharges = waterCharges;
	}

	public Long getWaterCharges() {
		return waterCharges;
	}

	public void setCleaning(Long cleaning) {
		this.cleaning = cleaning;
	}

	public Long getCleaning() {
		return cleaning;
	}

	public void setCouncilRates(Long councilRates) {
		this.councilRates = councilRates;
	}

	public Long getCouncilRates() {
		return councilRates;
	}

	public Long getGardening() {
		return gardening;
	}

	public void setGardening(Long gardening) {
		this.gardening = gardening;
	}

	public Long getTaxExpenses() {
		return taxExpenses;
	}

	public void setTaxExpenses(Long taxExpenses) {
		this.taxExpenses = taxExpenses;
	}

	public Long[] getAllOngoingCostValues(long propertyManagementFees) {
		Long[] ongoingCosts = new Long[] { getLandlordsInsurance(),
		        getMaintenance(), getStrata(), getWaterCharges(),
		        getCleaning(), getCouncilRates(), getGardening(),
		        getTaxExpenses(), getMiscOngoingExpenses(),
		        propertyManagementFees };
		return ongoingCosts;
	}

	public void projectBy(double cpi) {
		this.setCleaning(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getCleaning(), cpi)));
		this.setCouncilRates(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getCouncilRates(), cpi)));
		this.setGardening(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getGardening(), cpi)));
		this.setLandlordsInsurance(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getLandlordsInsurance(), cpi)));
		this.setMaintenance(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getMaintenance(), cpi)));
		this.setMiscOngoingExpenses(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getMiscOngoingExpenses(), cpi)));
		this.setStrata(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getStrata(), cpi)));
		this.setTaxExpenses(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getTaxExpenses(), cpi)));
		this.setWaterCharges(MathUtil.doubleToLong(MathUtil.increaseBy(
		        this.getWaterCharges(), cpi)));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
		        ToStringStyle.MULTI_LINE_STYLE);
	}

	public Long getTotalOngoingCosts(Long propertyManagementFees) {
		long ongoingCosts = 0l;
		for (Long cost : getAllOngoingCostValues(propertyManagementFees)) {
			ongoingCosts += cost;
		}
		return ongoingCosts;
	}
}
