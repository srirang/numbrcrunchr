package com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class OngoingCosts implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double DEFAULT_LANDLORD_INSURANCE = 400l;
    private static final double DEFAULT_MAINTENANCE = 100l;
    private static final double DEFAULT_WATER_CHARGES = 800l;
    private static final double DEFAULT_CLEANING = 100l;
    private static final double DEFAULT_COUNCIL_RATES = 1500l;
    private static final double DEFAULT_GARDENING = 100l;
    private static final double DEFAULT_TAX_EXPENSES = 100l;
    private static final double DEFAULT_MISC_ONGOING_EXPENSES = 0l;
    private static final double DEFAULT_STRATA = 0l;

    // Ongoing Costs
    @Column(name = "landlords_insurance")
    private double landlordsInsurance = DEFAULT_LANDLORD_INSURANCE;

    @Column(name = "maintenance")
    private double maintenance = DEFAULT_MAINTENANCE;

    @Column(name = "strata")
    private double strata = DEFAULT_STRATA;

    @Column(name = "water_charges")
    private double waterCharges = DEFAULT_WATER_CHARGES;

    @Column(name = "cleaning")
    private double cleaning = DEFAULT_CLEANING;

    @Column(name = "council_rates")
    private double councilRates = DEFAULT_COUNCIL_RATES;

    @Column(name = "gardening")
    private double gardening = DEFAULT_GARDENING;

    @Column(name = "tax_expenses")
    private double taxExpenses = DEFAULT_TAX_EXPENSES;

    @Column(name = "misc_ongoing_expenses")
    private double miscOngoingExpenses = DEFAULT_MISC_ONGOING_EXPENSES;

    public double getLandlordsInsurance() {
        return this.landlordsInsurance;
    }

    public void setLandlordsInsurance(double landlordsInsurance) {
        this.landlordsInsurance = landlordsInsurance;
    }

    public void setMaintenance(double maintenance) {
        this.maintenance = maintenance;
    }

    public double getMaintenance() {
        return this.maintenance;
    }

    public void setMiscOngoingExpenses(double miscOngoingExpenses) {
        this.miscOngoingExpenses = miscOngoingExpenses;
    }

    public double getMiscOngoingExpenses() {
        return this.miscOngoingExpenses;
    }

    public void setStrata(double strata) {
        this.strata = strata;
    }

    public double getStrata() {
        return this.strata;
    }

    public void setWaterCharges(double waterCharges) {
        this.waterCharges = waterCharges;
    }

    public double getWaterCharges() {
        return waterCharges;
    }

    public void setCleaning(double cleaning) {
        this.cleaning = cleaning;
    }

    public double getCleaning() {
        return cleaning;
    }

    public void setCouncilRates(double councilRates) {
        this.councilRates = councilRates;
    }

    public double getCouncilRates() {
        return councilRates;
    }

    public double getGardening() {
        return gardening;
    }

    public void setGardening(double gardening) {
        this.gardening = gardening;
    }

    public double getTaxExpenses() {
        return taxExpenses;
    }

    public void setTaxExpenses(double taxExpenses) {
        this.taxExpenses = taxExpenses;
    }

    public Double[] getAllOngoingCostValues(double propertyManagementFees) {
        Double[] ongoingCosts = new Double[] { getLandlordsInsurance(),
                getMaintenance(), getStrata(), getWaterCharges(),
                getCleaning(), getCouncilRates(), getGardening(),
                getTaxExpenses(), getMiscOngoingExpenses(),
                propertyManagementFees };
        return ongoingCosts;
    }

    public void projectBy(double cpi) {
        this.setCleaning(MathUtil.increaseBy(this.getCleaning(), cpi));
        this.setCouncilRates(MathUtil.increaseBy(this.getCouncilRates(), cpi));
        this.setGardening(MathUtil.increaseBy(this.getGardening(), cpi));
        this.setLandlordsInsurance(MathUtil.increaseBy(
                this.getLandlordsInsurance(), cpi));
        this.setMaintenance(MathUtil.increaseBy(this.getMaintenance(), cpi));
        this.setMiscOngoingExpenses(MathUtil.increaseBy(
                this.getMiscOngoingExpenses(), cpi));
        this.setStrata(MathUtil.increaseBy(this.getStrata(), cpi));
        this.setTaxExpenses(MathUtil.increaseBy(this.getTaxExpenses(), cpi));
        this.setWaterCharges(MathUtil.increaseBy(this.getWaterCharges(), cpi));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public double getTotalOngoingCosts(double propertyManagementFees) {
        double ongoingCosts = 0l;
        for (double cost : getAllOngoingCostValues(propertyManagementFees)) {
            ongoingCosts += cost;
        }
        return ongoingCosts;
    }
}
