package au.com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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

	public Long[] getOngoingCosts() {
		Long[] ongoingCosts = new Long[] { getLandlordsInsurance(),
				getMaintenance(), getStrata(), getWaterCharges(),
				getCleaning(), getCouncilRates(), getGardening(),
				getTaxExpenses(), getMiscOngoingExpenses() };
		return ongoingCosts;
	}

}