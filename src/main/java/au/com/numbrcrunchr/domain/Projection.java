package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Projection implements Serializable {
    private static final long serialVersionUID = -5278362123171908543L;

    private List<FeasibilityAnalysisResult> projections;
    private int cashflowPositiveYearIndex;

    public static Projection EMPTY_PROJECTION;
    static {
        EMPTY_PROJECTION = new Projection();
        EMPTY_PROJECTION.projections = Collections.emptyList();
    }

    private Projection() {

    }

    public Projection(List<FeasibilityAnalysisResult> projections) {
        if (projections == null || projections.isEmpty()) {
            throw new IllegalArgumentException(
                    "Projection needs at least one result to create");
        }
        this.projections = Collections.unmodifiableList(projections);
        for (int i = 0; i < projections.size(); i++) {
            FeasibilityAnalysisResult result = projections.get(i);
            if (result.isPositiveCashflow()) {
                cashflowPositiveYearIndex = i;
                break;
            }
        }
    }

    public double getAverageNettYield() {
        double nettYield = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            nettYield += projection.getNettYield();
        }
        nettYield = nettYield / projections.size();
        return nettYield;
    }

    public double getAverageGrossYield() {
        double grossYield = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            grossYield += projection.getGrossYield();
        }
        grossYield = grossYield / projections.size();
        return grossYield;
    }

    public long getTotalInterest() {
        long total = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            total += projection.getInterest();
        }
        return total;
    }

    public long getTotalRentalIncome() {
        long total = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            total += projection.getRentalIncome();
        }
        return total;
    }

    public long getTotalOngoingExpenses() {
        long total = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            total += projection.getOngoingExpenses();
        }
        return total;
    }

    public List<FeasibilityAnalysisResult> getProjections() {
        return projections;
    }

    public Double[] getAllGrossYields() {
        Double[] yields = new Double[projections.size()];
        int i = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            yields[i++] = projection.getGrossYield();
        }
        return yields;
    }

    public Double[] getAllNettYields() {
        Double[] yields = new Double[projections.size()];
        int i = 0;
        for (FeasibilityAnalysisResult projection : projections) {
            yields[i++] = projection.getNettYield();
        }
        return yields;
    }

    public int getCashflowPositiveYearIndex() {
        return cashflowPositiveYearIndex;
    }
}
