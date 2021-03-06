package com.numbrcrunchr.domain;

import java.io.Serializable;

public class ProjectionParameters implements Serializable {
    private static final long serialVersionUID = 1L;

    private double cpi;
    private double salaryIncreaseRate;
    private double rentIncreaseRate;
    private double capitalGrowthRate;

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
}
