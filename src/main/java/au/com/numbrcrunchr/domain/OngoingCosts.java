package au.com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class OngoingCosts implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Long DEFAULT_LANDLORD_INSURANCE = 400l;
	private static final Long DEFAULT_MAINTENANCE = 100l;
	private static final Long DEFAULT_WATER_CHARGES = 800l;
	private static final Long DEFAULT_CLEANING = 100l;
	private static final Long DEFAULT_COUNCIL_RATES = 1500l;
	private static final Long DEFAULT_GARDENING = 100l;
	private static final Long DEFAULT_TAX_EXPENSES = 100l;

	// Ongoing Costs
	@Column(name = "landlords_insurance")
	private Long landlordsInsurance;

	@Column(name = "maintenance")
	private Long maintenance;

	@Column(name = "strata")
	private Long strata;

	@Column(name = "water_charges")
	private Long waterCharges;

	@Column(name = "cleaning")
	private Long cleaning;

	@Column(name = "council_rates")
	private Long councilRates;

	@Column(name = "gardening")
	private Long gardening;

	@Column(name = "tax_expenses")
	private Long taxExpenses;

	@Column(name = "misc_ongoing_expenses")
	private Long miscOngoingExpenses;

	@Column(name = "property_management_fees")
	private Long propertyManagementFees;

	public Long getLandlordsInsurance() {
		return landlordsInsurance == null ? DEFAULT_LANDLORD_INSURANCE
				: landlordsInsurance;
	}

	public void setLandlordsInsurance(Long landlordsInsurance) {
		this.landlordsInsurance = landlordsInsurance;
	}

	public void setMaintenance(Long maintenance) {
		this.maintenance = maintenance;
	}

	public Long getMaintenance() {
		return maintenance == null ? DEFAULT_MAINTENANCE : maintenance;
	}

	public void setMiscOngoingExpenses(Long miscOngoingExpenses) {
		this.miscOngoingExpenses = miscOngoingExpenses;
	}

	public Long getMiscOngoingExpenses() {
		return miscOngoingExpenses == null ? 0 : miscOngoingExpenses;
	}

	public void setStrata(Long strata) {
		this.strata = strata;
	}

	public Long getStrata() {
		return strata == null ? 0 : strata;
	}

	public void setWaterCharges(Long waterCharges) {
		this.waterCharges = waterCharges;
	}

	public Long getWaterCharges() {
		return waterCharges == null ? DEFAULT_WATER_CHARGES : waterCharges;
	}

	public void setCleaning(Long cleaning) {
		this.cleaning = cleaning;
	}

	public Long getCleaning() {
		return cleaning == null ? DEFAULT_CLEANING : cleaning;
	}

	public void setCouncilRates(Long councilRates) {
		this.councilRates = councilRates;
	}

	public Long getCouncilRates() {
		return councilRates == null ? DEFAULT_COUNCIL_RATES : councilRates;
	}

	public Long getGardening() {
		return gardening == null ? DEFAULT_GARDENING : gardening;
	}

	public void setGardening(Long gardening) {
		this.gardening = gardening;
	}

	public Long getTaxExpenses() {
		return taxExpenses == null ? DEFAULT_TAX_EXPENSES : taxExpenses;
	}

	public void setTaxExpenses(Long taxExpenses) {
		this.taxExpenses = taxExpenses;
	}

	public Long[] getOngoingCosts(Long weeklyRent) {
		Long[] ongoingCosts = new Long[] { getLandlordsInsurance(),
				getMaintenance(), getStrata(), getWaterCharges(),
				getCleaning(), getCouncilRates(), getGardening(),
				getTaxExpenses(), getMiscOngoingExpenses(),
				getPropertyManagementFees() };
		return ongoingCosts;
	}

	public void setPropertyManagementFees(Long propertyManagementFees) {
		this.propertyManagementFees = propertyManagementFees;
	}

	public Long getPropertyManagementFees() {
		return propertyManagementFees == null ? 0 : propertyManagementFees;
	}

	public void projectBy(ProjectionParameters projectionParameters) {
		this.setCleaning(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getCleaning(), projectionParameters.getCpi())));
		this.setCouncilRates(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getCouncilRates(), projectionParameters.getCpi())));
		this.setGardening(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getGardening(), projectionParameters.getCpi())));
		this.setLandlordsInsurance(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getLandlordsInsurance(), projectionParameters.getCpi())));
		this.setMaintenance(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getMaintenance(), projectionParameters.getCpi())));
		this.setMiscOngoingExpenses(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getMiscOngoingExpenses(), projectionParameters.getCpi())));
		this.setPropertyManagementFees(MathUtil.doubleToLong(MathUtil
				.increaseBy(this.getPropertyManagementFees(),
						projectionParameters.getCpi())));
		this.setStrata(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getStrata(), projectionParameters.getCpi())));
		this.setTaxExpenses(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getTaxExpenses(), projectionParameters.getCpi())));
		this.setWaterCharges(MathUtil.doubleToLong(MathUtil.increaseBy(
				this.getWaterCharges(), projectionParameters.getCpi())));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Long getTotalOngoingCosts() {
		return this.cleaning + this.councilRates + this.gardening
				+ this.landlordsInsurance + this.maintenance
				+ this.miscOngoingExpenses + this.propertyManagementFees
				+ this.strata + this.taxExpenses + this.waterCharges;
	}
}
