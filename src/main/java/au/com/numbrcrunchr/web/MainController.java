package au.com.numbrcrunchr.web;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import au.com.numbrcrunchr.CsvExporter;
import au.com.numbrcrunchr.domain.FeasibilityAnalysisProjectionService;
import au.com.numbrcrunchr.domain.FeasibilityAnalysisResult;
import au.com.numbrcrunchr.domain.FinancialYearUtils;
import au.com.numbrcrunchr.domain.LVRCalculator;
import au.com.numbrcrunchr.domain.LoanBalanceCalculator;
import au.com.numbrcrunchr.domain.Owner;
import au.com.numbrcrunchr.domain.Projection;
import au.com.numbrcrunchr.domain.ProjectionParameters;
import au.com.numbrcrunchr.domain.Property;
import au.com.numbrcrunchr.domain.ReturnSummary;
import au.com.numbrcrunchr.domain.StampDutyCalculator;
import au.com.numbrcrunchr.domain.State;

import com.google.gson.Gson;

@ManagedBean
@SessionScoped
public class MainController implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean hasPartner = false;
    private final FacesMessage shareErrorMessage = new FacesMessage(
            "Ownership share must equal 100%");

    // Read-write
    private Boolean includesStampDuty;
    private boolean advancedSelected = false;
    private Property property;
    private Owner owner, partner;
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
        for (String frequency : FeasibilityAnalysisResult.FREQUENCY_FACTORS
                .keySet()) {
            FREQUENCIES.add(new SelectItem(frequency, frequency));
        }
    }

    public String performFeasibilityAnalysis() {
        updateTotals(null);
        this.advancedSelected = false;
        projection = feasibilityAnalysisProjectionService.applyProjectionFor(
                getProperty(), getNumberOfYears() - 1,
                getProjectionParameters());
        projectionResults = projection.getProjections();
        this.resultsAvailable = true;
        return null;
    }

    public void quickAnalysis() {
        updateTotals(null);
        projection = feasibilityAnalysisProjectionService.applyProjectionFor(
                getProperty(), getNumberOfYears() - 1,
                getProjectionParameters());
        projectionResults = projection.getProjections();
        this.resultsAvailable = true;
    }

    public String advanced() {
        this.advancedSelected = true;
        return null;
    }

    public String updateAssumptions() {
        return "main";
    }

    public String startAgain() {
        return "main";
    }

    public String apply() {
        this.advancedSelected = false;
        if (this.resultsAvailable) {
            performFeasibilityAnalysis();
        }
        return null;
    }

    public void downloadCsv(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext
                .getResponse();

        response.reset();
        response.setContentType("text/csv");
        response.setHeader("Content-disposition",
                "attachment; filename=\"numbrcrunchr-data-download.csv\"");
        try {
            response.getOutputStream()
                    .write(CsvExporter.exportToCsvString(projectionResults)
                            .getBytes());
        } catch (IOException e) {
            System.out.println(e);
        }
        facesContext.responseComplete();
    }

    public void updateTotals(AjaxBehaviorEvent event) {
        this.setStampDuty(getIncludesStampDuty() ? 0 : stampDutyCalculator
                .calculateStampDuty(getState(), getPropertyValue()));
        this.setTotalCost(getPropertyValue() + getStampDuty());
        this.setLoanAmount(loanBalanceCalculator.calculateLoanBalance(
                getPropertyValue(), getStampDuty(), getDeposit()));
        this.setDeposit(this.getTotalCost() - this.getLoanAmount());
        this.setLvr(new LVRCalculator().calculateLvr(this.getLoanAmount(),
                this.getTotalCost()));
    }

    public Long getTotalTenantPays() {
        Long totalTenantPays = 0l;
        for (FeasibilityAnalysisResult result : getProjection()) {
            totalTenantPays += result.getRentalIncome();
        }
        return totalTenantPays;
    }

    public Long getTotalYouPay() {
        Long totalYouPay = 0l;
        for (FeasibilityAnalysisResult result : getProjection()) {
            totalYouPay += result.getYouPay();
        }
        return totalYouPay;
    }

    public Long getTotalAtoPays() {
        Long totalAtoPays = 0l;
        for (FeasibilityAnalysisResult result : getProjection()) {
            totalAtoPays += result.getTaxSavings();
        }
        return totalAtoPays;
    }

    public String getJsonYears() {
        StringBuffer years = new StringBuffer("[");
        for (int i = 0; i < getNumberOfYears(); i++) {
            years.append("'Year ").append(i + 1).append("', ");
        }
        return years.substring(0, years.length() - 2).concat("]");
    }

    public String getAllIncomesAsJson() {
        StringBuffer json = new StringBuffer("[");
        int i = 0;
        int cashFlowPositive = projection.getCashflowPositiveYearIndex();
        for (FeasibilityAnalysisResult result : getProjection()) {
            if (i++ == cashFlowPositive) {
                json.append("{ y:");
                json.append(result.getTotalIncome());
                json.append(", marker: {symbol:'url(resources/images/dollarsign.png)'}"
                        + "},");
                continue;
            }
            json.append(result.getTotalIncome());
            json.append(", ");
        }
        String jsonString = json.toString();
        if (jsonString.endsWith(", ")) {
            jsonString = jsonString.substring(0, jsonString.length() - 2);
        }
        jsonString = jsonString + "]";
        return jsonString;
    }

    @SuppressWarnings("unchecked")
    public String getAllExpensesAsJson() {
        return new Gson().toJson(CollectionUtils.collect(getProjection(),
                new Transformer() {
                    @Override
                    public Object transform(Object input) {
                        return ((FeasibilityAnalysisResult) input)
                                .getTotalExpense();
                    }
                }).toArray(new Long[] {}));
    }

    @SuppressWarnings("unchecked")
    public String getAllOutOfPocketsAsJson() {
        return new Gson().toJson(CollectionUtils.collect(getProjection(),
                new Transformer() {
                    @Override
                    public Object transform(Object input) {
                        return ((FeasibilityAnalysisResult) input)
                                .getTotalOutOfPocket();
                    }
                }).toArray(new Long[] {}));
    }

    public String getAllGrossYieldsAsJson() {
        return new Gson().toJson(projection.getAllGrossYields());
    }

    public String getAllNettYieldsAsJson() {
        return new Gson().toJson(projection.getAllNettYields());
    }

    Property getProperty() {
        if (this.property == null) {
            this.property = new Property();
        }
        return this.property;
    }

    Owner getOwner() {
        if (this.owner == null) {
            this.owner = new Owner();
            this.property.addOwner(owner);
        }
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

    // Passthrough methods
    public void setState(String state) {
        this.getProperty().setState(state);
    }

    public String getState() {
        return this.getProperty().getState();
    }

    public Long getPropertyValue() {
        return getProperty().getPurchasePrice();
    }

    public void setPropertyValue(Long askingPrice) {
        getProperty().setPurchasePrice(askingPrice);
    }

    public Long getStampDuty() {
        return getProperty().getStampDuty();
    }

    public Long getTax() {
        return this.getOwner().getTax();
    }

    public Long getAnnualIncome() {
        return this.getOwner().getAnnualIncome();
    }

    public void setAnnualIncome(Long annualIncome) {
        this.getOwner().setAnnualIncome(annualIncome);
    }

    public Long getPartnerAnnualIncome() {
        return this.getPartner().getAnnualIncome();
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
        return includesStampDuty == null ? false : includesStampDuty;
    }

    public void setIncludesStampDuty(Boolean includesStampDuty) {
        this.includesStampDuty = includesStampDuty;
    }

    public Long getDeposit() {
        return this.getProperty().getDeposit();
    }

    public void setDeposit(Long deposit) {
        this.getProperty().setDeposit(deposit);
    }

    public Long getLoanAmount() {
        return this.getProperty().getLoanAmount();
    }

    public void setLoanAmount(Long loanAmount) {
        this.getProperty().setLoanAmount(loanAmount);
    }

    public Long getTotalCost() {
        return this.getProperty().getTotalCost();
    }

    public void setTotalCost(Long totalCost) {
        this.getProperty().setTotalCost(totalCost);
    }

    public Long getWeeklyRent() {
        return this.getProperty().getWeeklyRent();
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
        return this.getProperty().getLegalFees();
    }

    public Long getBuildingInspections() {
        return this.getProperty().getBuildingInspectionFees();
    }

    public Long getTitleRegistration() {
        return this.getProperty().getTitleRegistrationFees();
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
        return this.getProperty().getLoanApplicationFees();
    }

    public void setMortgageInsurance(Long mortgageInsurance) {
        this.getProperty().setMortgageInsurance(mortgageInsurance);
    }

    public Long getMortgageInsurance() {
        return this.getProperty().getMortgageInsurance();
    }

    public void setMortgageInsuranceStampDuty(Long mortgageInsuranceStampDuty) {
        this.getProperty().setMortgageInsuranceStampDuty(
                mortgageInsuranceStampDuty);
    }

    public Long getMortgageInsuranceStampDuty() {
        return this.getProperty().getMortgageInsuranceStampDuty();
    }

    public void setMortgageStampDuty(Long mortgageStampDuty) {
        this.getProperty().setMortgageStampDuty(mortgageStampDuty);
    }

    public Long getMortgageStampDuty() {
        return this.getProperty().getMortgageStampDuty();
    }

    public void setLandlordsInsurance(Long landlordsInsurance) {
        this.getProperty().setLandlordsInsurance(landlordsInsurance);
    }

    public Long getLandlordsInsurance() {
        return this.getProperty().getLandlordsInsurance();
    }

    public void setMaintenance(Long maintenance) {
        this.getProperty().setMaintenance(maintenance);
    }

    public Long getMaintenance() {
        return this.getProperty().getMaintenance();
    }

    public void setStrata(Long strata) {
        this.getProperty().setStrata(strata);
    }

    public Long getStrata() {
        return this.getProperty().getStrata();
    }

    public void setWaterCharges(Long waterCharges) {
        this.getProperty().setWaterCharges(waterCharges);
    }

    public Long getWaterCharges() {
        return this.getProperty().getWaterCharges();
    }

    public void setCleaning(Long cleaning) {
        this.getProperty().setCleaning(cleaning);
    }

    public Long getCleaning() {
        return this.getProperty().getCleaning();
    }

    public void setCouncilRates(Long councilRates) {
        this.getProperty().setCouncilRates(councilRates);
    }

    public Long getCouncilRates() {
        return this.getProperty().getCouncilRates();
    }

    public void setGardening(Long gardening) {
        this.getProperty().setGardening(gardening);
    }

    public Long getGardening() {
        return this.getProperty().getGardening();
    }

    public void setTaxExpenses(Long taxExpenses) {
        this.getProperty().setTaxExpenses(taxExpenses);
    }

    public Long getTaxExpenses() {
        return this.getProperty().getTaxExpenses();
    }

    public void setMiscOngoingExpenses(Long miscOngoingExpenses) {
        this.getProperty().setMiscOngoingExpenses(miscOngoingExpenses);
    }

    public Long getMiscOngoingExpenses() {
        return this.getProperty().getMiscOngoingExpenses();
    }

    public void setInterestRate(Double interestRate) {
        this.getProperty().setInterestRate(interestRate);
    }

    public Double getInterestRate() {
        return this.getProperty().getInterestRate();
    }

    public void setPropertyManagementFees(Double propertyManagementFees) {
        this.getProperty().setPropertyManagementFees(propertyManagementFees);
    }

    public Double getPropertyManagementFees() {
        return this.getProperty().getPropertyManagementFees();
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
        return this.getProperty().getBuildingValue();
    }

    public void setFittingsValue(Long fittingsValue) {
        this.getProperty().setFittingsValue(fittingsValue);
    }

    public Long getFittingsValue() {
        return this.getProperty().getFittingsValue();
    }

    public void setWeeksRented(Byte weeksRented) {
        this.getProperty().setWeeksRented(weeksRented);
    }

    public Byte getWeeksRented() {
        return this.getProperty().getWeeksRented();
    }

    // Dependencies
    @ManagedProperty(value = "#{stampDutyCalculator}")
    private static StampDutyCalculator stampDutyCalculator;

    @ManagedProperty(value = "#{loanBalanceCalculator}")
    private static LoanBalanceCalculator loanBalanceCalculator;

    @ManagedProperty(value = "#{projectionParameters}")
    private ProjectionParameters projectionParameters;

    @ManagedProperty(value = "#{feasibilityAnalysisProjectionService}")
    private static FeasibilityAnalysisProjectionService feasibilityAnalysisProjectionService;

    @ManagedProperty(value = "#{versionDetails}")
    private VersionDetails versionDetails;

    public void setLoanBalanceCalculator(
            LoanBalanceCalculator loanBalanceCalculator) {
        MainController.loanBalanceCalculator = loanBalanceCalculator;
    }

    public void setStampDutyCalculator(StampDutyCalculator stampDutyCalculator) {
        MainController.stampDutyCalculator = stampDutyCalculator;
    }

    public String getGearing() {
        return "Negative";
    }

    public Long getCashDeductions() {
        return getProperty().calculateTotalOngoingCost();
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
            FeasibilityAnalysisProjectionService projectionService) {
        MainController.feasibilityAnalysisProjectionService = projectionService;
    }

    public List<FeasibilityAnalysisResult> getProjection() {
        if (projectionResults == null) {
            projectionResults = new ArrayList<FeasibilityAnalysisResult>();
        }
        return projectionResults;
    }

    // FIXME: Can't be more than loan term, but there's no way to change loan
    // term (set to 30 years). Need to add validation to UI
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

    // public boolean isAdvancedSelected() {
    // return advancedSelected;
    // }

    public void setAdvancedSelected(boolean advancedSelected) {
        this.advancedSelected = advancedSelected;
    }

    // TODO: Remove this method when no longer used
    public boolean isShowErrors() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public boolean isHasErrors() {
        return isShowErrors();
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

    public boolean getHasErrors() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public String getCashflowPositiveYear() {
        return String.valueOf(Integer.valueOf(new SimpleDateFormat("yyyy")
                .format(getPurchaseDate()))
                + projection.getCashflowPositiveYearIndex());
    }

    public double getYear1GrossYield() {
        return projection.getAllGrossYields()[0];
    }

    public boolean isShowIntro() {
        return !advancedSelected && !resultsAvailable;
    }

    public boolean isShowAdvanced() {
        return advancedSelected;
    }

    public boolean isShowResults() {
        return resultsAvailable && !advancedSelected;
    }

    public ReturnSummary getFirstYearReturnSummary() {
        return new ReturnSummary(projection.getProjections().get(0));
    }

    public ReturnSummary getSecondYearReturnSummary() {
        return new ReturnSummary(projection.getProjections().get(1));
    }

    public List<FeasibilityAnalysisResult> getResults() {
        if (projection.getProjections().isEmpty()) {
            return Collections.emptyList();
        }
        return projection.getProjections().subList(
                0,
                projection.getProjections().size() < 25 ? projection
                        .getProjections().size() : 25);
    }

    public void changeFrequency(ValueChangeEvent e) {
        for (FeasibilityAnalysisResult result : getResults()) {
            result.changeFrequency(e.getNewValue().toString());
        }
    }

}
