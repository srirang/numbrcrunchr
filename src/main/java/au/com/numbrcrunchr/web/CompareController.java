package au.com.numbrcrunchr.web;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import au.com.numbrcrunchr.domain.FeasibilityAnalysisProjectionService;
import au.com.numbrcrunchr.domain.LVRCalculator;
import au.com.numbrcrunchr.domain.LoanBalanceCalculator;
import au.com.numbrcrunchr.domain.Owner;
import au.com.numbrcrunchr.domain.ProjectionParameters;
import au.com.numbrcrunchr.domain.Property;
import au.com.numbrcrunchr.domain.StampDutyCalculator;

@ManagedBean
@SessionScoped
public class CompareController implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int MAX_PROPERTIES = 10;

    private final List<Property> properties;
    private final Owner owner;
    private Long deposit;

    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public CompareController() {
        this.properties = new LinkedList<Property>();
        this.owner = new Owner();
        this.properties.add(newProperty());
    }

    private Property newProperty() {
        Property property = new Property(this.properties.size() + 1, owner);
        property.setDeposit(this.deposit);
        return property;
    }

    public void updateTotals(AjaxBehaviorEvent event) {
        for (Property property : properties) {
            property.setDeposit(this.deposit);
            property.setStampDuty(stampDutyCalculator.calculateStampDuty(
                    property.getState(), property.getPurchasePrice()));
            property.initialisePurhcaseCostAndMarketValue(property
                    .getPurchasePrice() + property.getStampDuty());
            property.setLoanAmount(loanBalanceCalculator.calculateLoanBalance(
                    property.getPurchasePrice(), property.getStampDuty(),
                    property.getDeposit()));
            property.setDeposit(property.getTotalPurchaseCost()
                    - property.getLoanAmount());
            property.setLvr(new LVRCalculator().calculateLvr(
                    property.getLoanAmount(), property.getTotalPurchaseCost()));
        }
    }

    public String add() {
        if (this.properties.size() >= MAX_PROPERTIES) {
            return null;
        }
        this.properties.add(newProperty());
        return null;
    }

    public String remove() {
        if (this.properties.size() == 1) {
            return null;
        }
        this.properties.remove(this.properties.size() - 1);
        return null;
    }

    public double getAnnualIncome() {
        return this.owner.getAnnualIncome();
    }

    public void setAnnualIncome(Long annualIncome) {
        this.owner.setAnnualIncome(annualIncome);
    }

    public Boolean getMedicareLevyApplies() {
        return this.owner.getMedicareLevyApplies();
    }

    public void setMedicareLevyApplies(Boolean medicareLevyApplies) {
        this.owner.setMedicareLevyApplies(medicareLevyApplies);
    }

    @ManagedProperty(value = "#{versionDetails}")
    private VersionDetails versionDetails;
    @ManagedProperty(value = "#{stampDutyCalculator}")
    private StampDutyCalculator stampDutyCalculator;

    @ManagedProperty(value = "#{loanBalanceCalculator}")
    private LoanBalanceCalculator loanBalanceCalculator;

    @ManagedProperty(value = "#{projectionParameters}")
    private ProjectionParameters projectionParameters;

    @ManagedProperty(value = "#{feasibilityAnalysisProjectionService}")
    private FeasibilityAnalysisProjectionService feasibilityAnalysisProjectionService;

    public VersionDetails getVersionDetails() {
        return versionDetails;
    }

    public void setFeasibilityAnalysisProjectionService(
            FeasibilityAnalysisProjectionService feasibilityAnalysisProjectionService) {
        this.feasibilityAnalysisProjectionService = feasibilityAnalysisProjectionService;
    }

    public void setLoanBalanceCalculator(
            LoanBalanceCalculator loanBalanceCalculator) {
        this.loanBalanceCalculator = loanBalanceCalculator;
    }

    public void setProjectionParameters(
            ProjectionParameters projectionParameters) {
        this.projectionParameters = projectionParameters;
    }

    public void setStampDutyCalculator(StampDutyCalculator stampDutyCalculator) {
        this.stampDutyCalculator = stampDutyCalculator;
    }

    public FeasibilityAnalysisProjectionService getFeasibilityAnalysisProjectionService() {
        return feasibilityAnalysisProjectionService;
    }

    public LoanBalanceCalculator getLoanBalanceCalculator() {
        return loanBalanceCalculator;
    }

    public ProjectionParameters getProjectionParameters() {
        return projectionParameters;
    }

    public StampDutyCalculator getStampDutyCalculator() {
        return stampDutyCalculator;
    }

    public void setVersionDetails(VersionDetails versionDetails) {
        this.versionDetails = versionDetails;
    }

    public String getVersionNumber() {
        return versionDetails.getVersionNumber();
    }

    public List<SelectItem> getStates() {
        return MainController.STATES;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }
}
