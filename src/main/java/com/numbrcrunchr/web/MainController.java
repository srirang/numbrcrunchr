package com.numbrcrunchr.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.numbrcrunchr.domain.FeasibilityAnalysisProjectionService;
import com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import com.numbrcrunchr.domain.FinancialYearUtils;
import com.numbrcrunchr.domain.LVRCalculator;
import com.numbrcrunchr.domain.LoanBalanceCalculator;
import com.numbrcrunchr.domain.MathUtil;
import com.numbrcrunchr.domain.Owner;
import com.numbrcrunchr.domain.Projection;
import com.numbrcrunchr.domain.ProjectionParameters;
import com.numbrcrunchr.domain.Property;
import com.numbrcrunchr.domain.StampDutyCalculator;
import com.numbrcrunchr.domain.State;

@ManagedBean
@SessionScoped
public class MainController implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean hasPartner = false;
    private final FacesMessage shareErrorMessage = new FacesMessage(
            "Ownership share must equal 100%");

    // Dependencies
    @ManagedProperty(value = "#{stampDutyCalculator}")
    private StampDutyCalculator stampDutyCalculator;

    @ManagedProperty(value = "#{loanBalanceCalculator}")
    private LoanBalanceCalculator loanBalanceCalculator;

    @ManagedProperty(value = "#{projectionParameters}")
    private ProjectionParameters projectionParameters;

    @ManagedProperty(value = "#{feasibilityAnalysisProjectionService}")
    private FeasibilityAnalysisProjectionService feasibilityAnalysisProjectionService;

    @ManagedProperty(value = "#{versionDetails}")
    private VersionDetails versionDetails;

    // Read-write
    private Boolean includesStampDuty = Boolean.FALSE;
    private Property property = new Property();
    private final Owner owner = new Owner();
    private Owner partner = new Owner();
    private boolean resultsAvailable = false;
    private int numberOfYears = 25;
    private String frequency = FeasibilityAnalysisResult.YEARLY;

    // Read only
    private List<FeasibilityAnalysisResult> projectionResults;
    private Projection projection = Projection.EMPTY_PROJECTION;
    public static final List<SelectItem> STATES;
    static {
        STATES = new ArrayList<SelectItem>(State.STATES.size());
        for (String state : State.STATES) {
            STATES.add(new SelectItem(state, state));
        }
    }

    public static final List<SelectItem> FREQUENCIES;
    static {
        FREQUENCIES = new ArrayList<SelectItem>(
                FeasibilityAnalysisResult.FREQUENCY_FACTORS.size());
        for (String frequency : FeasibilityAnalysisResult.FREQUENCIES) {
            FREQUENCIES.add(new SelectItem(frequency, frequency));
        }
    }

    public String runProjection() {
        updateTotals(null);
        projection = feasibilityAnalysisProjectionService.runProjection(
                getProperty(), getNumberOfYears() - 1,
                getProjectionParameters());
        projectionResults = projection.getProjections();
        this.resultsAvailable = true;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Numbers Crunched!",
                    "Select Download to download the raw data"));
        }
        return null;
    }

    public void updateTotals(AjaxBehaviorEvent event) {
        this.setStampDuty(getIncludesStampDuty() ? Long.valueOf(0) : MathUtil
                .doubleToLong(stampDutyCalculator.calculateStampDuty(
                        getState(), getPropertyValue())));
        this.getProperty().initialisePurhcaseCostAndMarketValue(
                getPropertyValue() + getStampDuty() + getGovernmentCosts()
                        + getConveyancingCost());
        this.setLoanAmount(MathUtil.doubleToLong(loanBalanceCalculator
                .calculateLoanBalance(getPropertyValue(), getStampDuty(),
                        getDeposit())));
        this.setDeposit(this.getTotalPurhcaseCost() - this.getLoanAmount());
        this.setLvr(new LVRCalculator().calculateLvr(this.getLoanAmount(),
                this.getTotalPurhcaseCost()));
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (this.getLvr() > 80) {
            if (facesContext != null) {
                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN,
                        "Lender's Mortgage Insurance",
                        "Lender's mortgage insurance may be applicable"));
            }
        } else {
            this.setLendersMortgageInsurance(0l);
        }
    }

    Property getProperty() {
        return this.property;
    }

    Owner getOwner() {
        return this.owner;
    }

    Owner getPartner() {
        if (this.partner == null) {
            this.partner = new Owner();
            this.property.addOwner(partner);
        }
        return this.partner;
    }

    public List<SelectItem> getStates() {
        return STATES;
    }

    public List<SelectItem> getFrequencies() {
        return FREQUENCIES;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setState(String state) {
        this.getProperty().setState(state);
    }

    public String getState() {
        return this.getProperty().getState();
    }

    public Long getPropertyValue() {
        return MathUtil.doubleToLong(getProperty().getPurchasePrice());
    }

    public void setPropertyValue(Long askingPrice) {
        getProperty().setPurchasePrice(askingPrice);
    }

    public Long getStampDuty() {
        return MathUtil.doubleToLong(getProperty().getStampDuty());
    }

    public Long getTax() {
        return MathUtil.doubleToLong(this.getOwner().getTax());
    }

    public Long getAnnualIncome() {
        return MathUtil.doubleToLong(this.getOwner().getAnnualIncome());
    }

    public void setAnnualIncome(Long annualIncome) {
        this.getOwner().setAnnualIncome(annualIncome);
    }

    public Long getPartnerAnnualIncome() {
        return MathUtil.doubleToLong(this.getPartner().getAnnualIncome());
    }

    public void setPartnerAnnualIncome(Long annualIncome) {
        this.getPartner().setAnnualIncome(annualIncome);
    }

    public Boolean getMedicareLevyApplies() {
        return this.getOwner().getMedicareLevyApplies();
    }

    public void setMedicareLevyApplies(Boolean medicareLevyApplies) {
        this.getOwner().setMedicareLevyApplies(medicareLevyApplies);
    }

    public Boolean getPartnerMedicareLevyApplies() {
        return this.getPartner().getMedicareLevyApplies();
    }

    public void setPartnerMedicareLevyApplies(Boolean medicareLevyApplies) {
        this.getPartner().setMedicareLevyApplies(medicareLevyApplies);
    }

    public Boolean getIncludesStampDuty() {
        return includesStampDuty;
    }

    public void setIncludesStampDuty(Boolean includesStampDuty) {
        this.includesStampDuty = includesStampDuty;
    }

    public Long getDeposit() {
        return MathUtil.doubleToLong(this.getProperty().getDeposit());
    }

    public void setDeposit(Long deposit) {
        this.getProperty().setDeposit(deposit);
    }

    public Long getLoanAmount() {
        return MathUtil.doubleToLong(this.getProperty().getLoanAmount());
    }

    public void setLoanAmount(Long loanAmount) {
        this.getProperty().setLoanAmount(loanAmount);
    }

    public Long getTotalPurhcaseCost() {
        return MathUtil.doubleToLong(this.getProperty().getTotalPurchaseCost());
    }

    public Long getWeeklyRent() {
        return MathUtil.doubleToLong(this.getProperty().getWeeklyRent());
    }

    public void setWeeklyRent(Long weeklyRent) {
        this.getProperty().setWeeklyRent(weeklyRent);
    }

    public void setPropertyAddress(String propertyAddress) {
        this.getProperty().setAddress(propertyAddress);
    }

    public String getPropertyAddress() {
        return this.getProperty().getAddress();
    }

    public void setStampDuty(Long stampDuty) {
        this.getProperty().setStampDuty(stampDuty);
    }

    public void setLegalFees(Long legalFees) {
        this.getProperty().setLegalFees(legalFees);
    }

    public Long getLegalFees() {
        return MathUtil.doubleToLong(this.getProperty().getLegalFees());
    }

    public Long getBuildingInspections() {
        return MathUtil.doubleToLong(this.getProperty()
                .getBuildingInspectionFees());
    }

    public Long getTitleRegistration() {
        return MathUtil.doubleToLong(this.getProperty()
                .getTitleRegistrationFees());
    }

    public void setBuildingInspections(Long buildingInspections) {
        this.getProperty().setBuildingInspectionFees(buildingInspections);
    }

    public void setTitleRegistration(Long titleRegistration) {
        this.getProperty().setTitleRegistrationFees(titleRegistration);
    }

    public void setLoanApplicationFees(Long loanApplicationFee) {
        this.getProperty().setLoanApplicationFees(loanApplicationFee);
    }

    public Long getLoanApplicationFees() {
        return MathUtil.doubleToLong(this.getProperty()
                .getLoanApplicationFees());
    }

    public void setLendersMortgageInsurance(Long mortgageInsurance) {
        this.getProperty().setLendersMortgageInsurance(mortgageInsurance);
    }

    public Long getLendersMortgageInsurance() {
        return MathUtil.doubleToLong(this.getProperty()
                .getLendersMortgageInsurance());
    }

    public void setMortgageInsuranceStampDuty(Long mortgageInsuranceStampDuty) {
        this.getProperty().setMortgageInsuranceStampDuty(
                mortgageInsuranceStampDuty);
    }

    public Long getMortgageInsuranceStampDuty() {
        return MathUtil.doubleToLong(this.getProperty()
                .getMortgageInsuranceStampDuty());
    }

    public void setMortgageStampDuty(Long mortgageStampDuty) {
        this.getProperty().setMortgageStampDuty(mortgageStampDuty);
    }

    public Long getMortgageStampDuty() {
        return MathUtil.doubleToLong(this.getProperty().getMortgageStampDuty());
    }

    public void setLandlordsInsurance(Long landlordsInsurance) {
        this.getProperty().setLandlordsInsurance(landlordsInsurance);
    }

    public Long getLandlordsInsurance() {
        return MathUtil
                .doubleToLong(this.getProperty().getLandlordsInsurance());
    }

    public void setMaintenance(Long maintenance) {
        this.getProperty().setMaintenance(maintenance);
    }

    public Long getMaintenance() {
        return MathUtil.doubleToLong(this.getProperty().getMaintenance());
    }

    public void setStrata(Long strata) {
        this.getProperty().setStrata(strata);
    }

    public Long getStrata() {
        return MathUtil.doubleToLong(this.getProperty().getStrata());
    }

    public void setWaterCharges(Long waterCharges) {
        this.getProperty().setWaterCharges(waterCharges);
    }

    public Long getWaterCharges() {
        return MathUtil.doubleToLong(this.getProperty().getWaterCharges());
    }

    public void setCleaning(Long cleaning) {
        this.getProperty().setCleaning(cleaning);
    }

    public Long getCleaning() {
        return MathUtil.doubleToLong(this.getProperty().getCleaning());
    }

    public void setCouncilRates(Long councilRates) {
        this.getProperty().setCouncilRates(councilRates);
    }

    public Long getCouncilRates() {
        return MathUtil.doubleToLong(this.getProperty().getCouncilRates());
    }

    public void setGardening(Long gardening) {
        this.getProperty().setGardening(gardening);
    }

    public Long getGardening() {
        return MathUtil.doubleToLong(this.getProperty().getGardening());
    }

    public void setTaxExpenses(Long taxExpenses) {
        this.getProperty().setTaxExpenses(taxExpenses);
    }

    public Long getTaxExpenses() {
        return MathUtil.doubleToLong(this.getProperty().getTaxExpenses());
    }

    public void setMiscOngoingExpenses(Long miscOngoingExpenses) {
        this.getProperty().setMiscOngoingExpenses(miscOngoingExpenses);
    }

    public Long getMiscOngoingExpenses() {
        return MathUtil.doubleToLong(this.getProperty()
                .getMiscOngoingExpenses());
    }

    public void setInterestRate(Double interestRate) {
        this.getProperty().setInterestRate(interestRate);
    }

    public Double getInterestRate() {
        return this.getProperty().getInterestRate();
    }

    public void setPropertyManagementFees(Double managementFeeRate) {
        this.getProperty().setManagementFeeRate(managementFeeRate);
    }

    public Double getPropertyManagementFees() {
        return this.getProperty().getManagementFeeRate();
    }

    public void setConstructionDate(Date constructionDate) {
        this.getProperty().setConstructionDate(constructionDate);
    }

    public Date getConstructionDate() {
        return this.getProperty().getConstructionDate();
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.getProperty().setPurchaseDate(purchaseDate);
    }

    public Date getPurchaseDate() {
        return this.getProperty().getPurchaseDate();
    }

    public void setBuildingValue(Long buildingValue) {
        this.getProperty().setBuildingValue(buildingValue);
    }

    public Long getBuildingValue() {
        return MathUtil.doubleToLong(this.getProperty().getBuildingValue());
    }

    public void setFittingsValue(Long fittingsValue) {
        this.getProperty().setFittingsValue(fittingsValue);
    }

    public Long getFittingsValue() {
        return MathUtil.doubleToLong(this.getProperty().getFittingsValue());
    }

    public void setWeeksRented(Byte weeksRented) {
        this.getProperty().setWeeksRented(weeksRented);
    }

    public Byte getWeeksRented() {
        return this.getProperty().getWeeksRented();
    }

    public void setLoanBalanceCalculator(
            LoanBalanceCalculator loanBalanceCalculator) {
        this.loanBalanceCalculator = loanBalanceCalculator;
    }

    public void setStampDutyCalculator(StampDutyCalculator stampDutyCalculator) {
        this.stampDutyCalculator = stampDutyCalculator;
    }

    public ProjectionParameters getProjectionParameters() {
        return projectionParameters;
    }

    public Date getEndOfFinancialYear() {
        return FinancialYearUtils.getEndOfFinancialYear(new Date());
    }

    public void setProjectionParameters(
            ProjectionParameters projectionParameters) {
        this.projectionParameters = projectionParameters;
    }

    public void setFeasibilityAnalysisProjectionService(
            FeasibilityAnalysisProjectionService feasibilityAnalysisProjectionService) {
        this.feasibilityAnalysisProjectionService = feasibilityAnalysisProjectionService;
    }

    public List<FeasibilityAnalysisResult> getProjection() {
        if (projectionResults == null) {
            projectionResults = new ArrayList<FeasibilityAnalysisResult>();
        }
        return projectionResults;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public StampDutyCalculator getStampDutyCalculator() {
        return stampDutyCalculator;
    }

    public LoanBalanceCalculator getLoanBalanceCalculator() {
        return loanBalanceCalculator;
    }

    void setProperty(Property property) {
        this.property = property;
    }

    public double getCpi() {
        return getProjectionParameters().getCpi();
    }

    public void setCpi(double cpi) {
        getProjectionParameters().setCpi(cpi);
    }

    public double getCapitalGrowthRate() {
        return getProjectionParameters().getCapitalGrowthRate();
    }

    public void setCapitalGrowthRate(double capitalGrowthRate) {
        getProjectionParameters().setCapitalGrowthRate(capitalGrowthRate);
    }

    public double getRentIncreaseRate() {
        return getProjectionParameters().getRentIncreaseRate();
    }

    public void setRentIncreaseRate(double rentIncreaseRate) {
        getProjectionParameters().setRentIncreaseRate(rentIncreaseRate);
    }

    public double getSalaryIncreaseRate() {
        return getProjectionParameters().getSalaryIncreaseRate();
    }

    public void setSalaryIncreaseRate(double salaryIncreaseRate) {
        getProjectionParameters().setSalaryIncreaseRate(salaryIncreaseRate);
    }

    public int getInterestOnlyPeriod() {
        return this.getProperty().getInterestOnlyPeriod();
    }

    public void setInterestOnlyPeriod(int interestOnlyPeriod) {
        this.getProperty().setInterestOnlyPeriod(interestOnlyPeriod);
    }

    public Double getOwnerShare() {
        return getOwner().getOwnershipShare();
    }

    public void validateTotalShare(AjaxBehaviorEvent event) {
        if (getPartner().getOwnershipShare() + getOwner().getOwnershipShare() != 100
                && !FacesContext.getCurrentInstance().getMessageList()
                        .contains(shareErrorMessage)) {
            FacesContext.getCurrentInstance().addMessage("", shareErrorMessage);
        }
    }

    public void setOwnerShare(Double share) {
        getOwner().setOwnershipShare(share);
    }

    public Double getPartnerShare() {
        return getPartner().getOwnershipShare();
    }

    public void setPartnerShare(Double share) {
        getPartner().setOwnershipShare(share);
    }

    public Double getLvr() {
        return getProperty().getLvr();
    }

    public void setLvr(Double lvr) {
        getProperty().setLvr(lvr);
    }

    public Integer getLoanTerm() {
        return getProperty().getLoanTerm();
    }

    public void setLoanTerm(Integer loanTerm) {
        getProperty().setLoanTerm(loanTerm);
    }

    public boolean isHasPartner() {
        return hasPartner;
    }

    public void setHasPartner(boolean hasPartner) {
        this.hasPartner = hasPartner;
    }

    public void setVersionDetails(VersionDetails versionDetails) {
        this.versionDetails = versionDetails;
    }

    public String getVersionNumber() {
        return versionDetails.getVersionNumber();
    }

    public String getContactEmail() {
        return versionDetails.getContactEmail();
    }

    public boolean getHasErrors() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public String getCashflowPositiveYear() {
        return String.valueOf(Integer.valueOf(new SimpleDateFormat("yyyy")
                .format(getPurchaseDate()))
                + projection.getCashflowPositiveYearIndex());
    }

    public boolean isShowResults() {
        return resultsAvailable;
    }

    public List<FeasibilityAnalysisResult> getResults() {
        return this.projection.getProjections();
    }

    public void changeFrequency() {
        for (FeasibilityAnalysisResult result : getResults()) {
            result.changeFrequency(frequency);
        }
    }

    protected FeasibilityAnalysisProjectionService getFeasibilityAnalysisProjectionService() {
        return feasibilityAnalysisProjectionService;
    }

    // TODO Consider the best place to cache and update the chart
    public CartesianChartModel getCashflowChart() {
        CartesianChartModel chartModel = new CartesianChartModel();
        ChartSeries incomeSeries = new ChartSeries("Income");
        ChartSeries expenseSeries = new ChartSeries("Expense");
        ChartSeries outOfPocketSeries = new ChartSeries("Out of Pocket");
        for (FeasibilityAnalysisResult result : getProjection()) {
            incomeSeries.set(result.getYear(), result.getTotalIncome());
            expenseSeries.set(result.getYear(), result.getTotalExpense());
            outOfPocketSeries.set(result.getYear(), result.getOutOfPocket());
        }
        chartModel.addSeries(incomeSeries);
        chartModel.addSeries(expenseSeries);
        chartModel.addSeries(outOfPocketSeries);
        return chartModel;
    }

    // TODO Consider the best place to cache and update the chart
    public CartesianChartModel getEquityChart() {
        CartesianChartModel chartModel = new CartesianChartModel();
        ChartSeries equitySeries = new ChartSeries("Equity");
        ChartSeries loanBalanceSeries = new ChartSeries("Loan Balance");
        ChartSeries marketValueSeries = new ChartSeries("Market Value");
        for (FeasibilityAnalysisResult result : getProjection()) {
            equitySeries.set(result.getYear(), result.getEquityAvailable());
            loanBalanceSeries.set(result.getYear(), result.getLoanBalance());
            marketValueSeries.set(result.getYear(), result.getPropertyValue());
        }
        chartModel.addSeries(equitySeries);
        chartModel.addSeries(loanBalanceSeries);
        chartModel.addSeries(marketValueSeries);
        return chartModel;
    }

    public String getCashflowTipFormat() {
        return "<span style=\"display:none;\">%s</span><span>%s</span>";
    }

    public Long getGovernmentCosts() {
        return MathUtil.doubleToLong(this.getProperty().getGovermentCosts());
    }

    public void setGovernmentCosts(Long governmentCosts) {
        this.getProperty().setGovernmentCosts(governmentCosts);
    }

    public Long getConveyancingCost() {
        return MathUtil.doubleToLong(this.getProperty().getConveyancingCost());
    }

    public void setConveyancingCost(Long conveyancingCost) {
        this.getProperty().setConveyancingCost(conveyancingCost);
    }

    public void validatePrice(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error:",
                    "Please enter a value between $10,000 and $5,000,000"));
            return;
        }
        if ((Long) value <= 10000 || (Long) value >= 5000000) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error:",
                    "Please enter a value between $10,000 and $5,000,000"));
            return;
        }
    }

    public void validateWeeklyRent(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value == null) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error:",
                    "Please enter a value between $100 and $5,000"));
            return;
        }
        if ((Long) value <= 100 || (Long) value >= 5000) {
            context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error:",
                    "Please enter a value between $100 and $5,000"));
            return;
        }
    }

    public boolean isLmiApplicable() {
        return this.getLvr() <= 80;
    }

    public MainController() {
        this.property.addOwner(owner);
    }

    public boolean getShowResults() {
        return resultsAvailable;
    }

}
