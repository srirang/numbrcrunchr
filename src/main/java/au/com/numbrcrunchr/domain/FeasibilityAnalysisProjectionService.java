package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeasibilityAnalysisProjectionService implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String PROJECTION_YEAR_PREFIX = "Y ";
    private FeasibilityAnalyser feasibilityAnalyser;
    private LoanAmortisationScheduleCalculator loanAmortisationScheduleCalculator;

    public Projection runProjection(Property property, int numberOfYears,
            ProjectionParameters projectionParameters) {
        FeasibilityAnalysisResult firstFullYearResult = feasibilityAnalyser
                .analyseFirstYearFeasibility(property);
        int projectionYears = property.getLoanTerm() < numberOfYears ? property
                .getLoanTerm() : numberOfYears;
        List<FeasibilityAnalysisResult> projections = new ArrayList<FeasibilityAnalysisResult>(
                projectionYears);
        projections.add(firstFullYearResult);

        AmortisationSchedule amortisationSchedule = loanAmortisationScheduleCalculator
                .calculateAmortisationSchedule(property.getLoanAmount(),
                        property.getInterestOnlyPeriod(),
                        property.getLoanTerm(), property.getInterestRate());

        FeasibilityAnalysisResult result;
        Property projectionProperty = null;
        try {
            projectionProperty = (Property) property.clone();
        } catch (CloneNotSupportedException e) {
            throw new DataException("Error completing projection", e);
        }
        int j = 0;
        for (int i = 1; i < projectionYears + 1; i++) {
            try {
                projectionProperty = (Property) projectionProperty.clone();
                projectionProperty.projectBy(projectionParameters);
                // TODO: Test this, may not be right
                Repayment yearlyRepayment = amortisationSchedule
                        .getYearlyRepayments().get(i - 1);
                result = feasibilityAnalyser.analyseFeasibility(
                        projectionProperty, yearlyRepayment.getInterest()
                                .doubleValue(), yearlyRepayment.getPrincipal()
                                .doubleValue(), PROJECTION_YEAR_PREFIX
                                + (i + 1));
                projectionProperty.setLoanAmount(yearlyRepayment
                        .getLoanBalance());
                projections.add(result);
            } catch (CloneNotSupportedException e) {
                throw new DataException("Error completing projection", e);
            }
        }
        return new Projection(projections, projectionParameters);
    }

    public void setFeasibilityAnalyser(FeasibilityAnalyser feasibilityAnalyser) {
        this.feasibilityAnalyser = feasibilityAnalyser;
    }

    public void setLoanAmortisationScheduleCalculator(
            LoanAmortisationScheduleCalculator loanAmortisationScheduleCalculator) {
        this.loanAmortisationScheduleCalculator = loanAmortisationScheduleCalculator;
    }
}
