package au.com.numbrcrunchr.domain;

import java.io.Serializable;

public class ProjectionParameters implements Serializable {
	private static final long serialVersionUID = 1L;

	private double cpi;
	private double salaryIncreaseRate;
	private double rentIncreaseRate;
	private double capitalGrowthRate;
	private double propertyManagementFeeRate;

	public double getCapitalGrowthRate() {
		return capitalGrowthRate;
	}

	public double getCpi() {
		return cpi;
	}

	public double getRentIncreaseRate() {
		return rentIncreaseRate;
	}

	public double getSalaryIncreaseRate() {
		return salaryIncreaseRate;
	}

	public double getPropertyManagementFeeRate() {
		return propertyManagementFeeRate;
	}

	public void setCapitalGrowthRate(double capitalGrowthRate) {
		this.capitalGrowthRate = capitalGrowthRate;
	}

	public void setCpi(double cpi) {
		this.cpi = cpi;
	}

	public void setRentIncreaseRate(double rentIncreaseRate) {
		this.rentIncreaseRate = rentIncreaseRate;
	}

	public void setSalaryIncreaseRate(double salaryIncreaseRate) {
		this.salaryIncreaseRate = salaryIncreaseRate;
	}

	public void setPropertyManagementFeeRate(double propertyManagementFeeRate) {
		this.propertyManagementFeeRate = propertyManagementFeeRate;
	}

	public double getCapitalGrowthPercentage() {
		return capitalGrowthRate / 100;
	}

	public double getCpiPercentage() {
		return cpi / 100;
	}

	public double getRentIncreasePercentage() {
		return rentIncreaseRate / 100;
	}

	public double getSalaryIncreasePercentage() {
		return salaryIncreaseRate / 100;
	}

	public double getPropertyManagementFeePercentage() {
		return propertyManagementFeeRate / 100;
	}
}
