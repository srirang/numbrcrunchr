package com.numbrcrunchr.domain;

public class InvestmentProjectionReport {
    private final Projection projection;

    public InvestmentProjectionReport(Projection projection) {
        this.projection = projection;
    }

    public String getAddress() {
        return this.projection.getProperty().getAddress();
    }

    public Long getPurchasePrice() {
        return MathUtil.doubleToLong(this.projection.getProperty()
                .getPurchasePrice());
    }
}
