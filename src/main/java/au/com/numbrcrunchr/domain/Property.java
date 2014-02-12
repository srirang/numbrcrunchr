package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author AMIS005
 */
@Entity
@Table(name = "property")
@NamedQueries({
        @NamedQuery(name = "Property.findAll", query = "SELECT p FROM Property p"),
        @NamedQuery(name = "Property.findByIdProperty", query = "SELECT p FROM Property p WHERE p.idProperty = :idProperty"),
        @NamedQuery(name = "Property.findByAddress", query = "SELECT p FROM Property p WHERE p.address = :address"),
        @NamedQuery(name = "Property.findByState", query = "SELECT p FROM Property p WHERE p.state = :state"),
        @NamedQuery(name = "Property.findByPrice", query = "SELECT p FROM Property p WHERE p.purchasePrice = :askingPrice"),
        @NamedQuery(name = "Property.findByPurchaseDate", query = "SELECT p FROM Property p WHERE p.purchaseDate = :purchaseDate") })
public class Property implements Serializable, Cloneable {
    private static final Long DEFAULT_TITLE_REGISTRATION_FEES = 300l;
    private static final Long DEFAULT_LEGAL_FEES = 800l;

    private static final long serialVersionUID = 1L;
    private static final Byte DEFAULT_WEEKS_RENTED = 50;
    private static final Integer DEFAULT_LOAN_TERM = 30;
    private static final Double DEFAULT_INTEREST_RATE = 6.0;
    private static final int DEFAULT_INTEREST_ONLY_PERIOD = 10;
    private static final Double DEFAULT_LVR = 80.0;
    private static final Double DEFAULT_PROPERTY_MANAGEMENT_FEES = 8.8d;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProperty", nullable = false)
    private Integer idProperty;

    @Column(name = "total_cost")
    private Long totalPurchaseCost;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "loan_amount")
    private Long loanAmount;

    @Column(name = "weekly_rent")
    private Long weeklyRent;

    @Column(name = "management_fee_rate")
    private Double managementFeeRate;

    @Basic(optional = false)
    @Column(name = "state", nullable = false, length = 45)
    private String state;

    @Column(name = "purchase_price")
    private Long purchasePrice;

    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @ManyToMany(mappedBy = "propertyList", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Owner> ownerList;

    // Buying costs
    @Column(name = "stamp_duty")
    private Long stampDuty;

    @Column(name = "legal_fees")
    private Long legalFees;

    @Column(name = "interest_only_period")
    private Integer interestOnlyPeriod;

    @Column(name = "building_inspection_fees")
    private Long buildingInspectionFees;

    @Column(name = "title_registration_fees")
    private Long titleRegistrationFees;

    // Borrowing costs
    @Column(name = "mortgage_stamp_duty")
    private Long mortgageStampDuty;

    @Column(name = "mortgage_insurance")
    private Long mortgageInsurance;

    @Column(name = "mortgage_insurance_stamp_duty")
    private Long mortgageInsuranceStampDuty;

    @Column(name = "loan_application_fees")
    private Long loanApplicationFees;

    @Embedded
    private OngoingCosts ongoingCosts = new OngoingCosts();

    // Assumptions
    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "lvr")
    private Double lvr = DEFAULT_LVR;

    @Column(name = "market_value")
    private Long marketValue;

    @Column(name = "construction_date")
    @Temporal(TemporalType.DATE)
    private Date constructionDate;

    @Column(name = "building_value")
    private Long buildingValue;

    @Column(name = "fittings_value")
    private Long fittingsValue;

    @Column(name = "weeks_rented")
    private Byte weeksRented;

    @Column(name = "loan_term")
    private Integer loanTerm;

    public Property() {
        this.ownerList = new ArrayList<Owner>();
    }

    public Property(int idProperty, Owner owner) {
        this();
        this.idProperty = idProperty;
        this.ownerList.add(owner);
    }

    public Integer getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Integer idProperty) {
        this.idProperty = idProperty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPurchasePrice() {
        return purchasePrice == null ? 0 : purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
        initialisePurhcaseCostAndMarketValue(purchasePrice);
    }

    public List<Owner> getOwnerList() {
        if (this.ownerList == null) {
            this.ownerList = new ArrayList<Owner>();
        }
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    public void addOwner(Owner owner) {
        getOwnerList().add(owner);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProperty != null ? idProperty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if ((this.idProperty == null && other.idProperty != null)
                || (this.idProperty != null && !this.idProperty
                        .equals(other.idProperty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Long getStampDuty() {
        return stampDuty == null ? 0 : stampDuty;
    }

    public void setStampDuty(Long stampDuty) {
        this.stampDuty = stampDuty;
    }

    public Long getDeposit() {
        return deposit == null ? 0 : deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Long getLoanAmount() {
        return loanAmount == null ? 0 : loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Long getTotalPurchaseCost() {
        return totalPurchaseCost == null ? 0 : totalPurchaseCost;
    }

    public void initialisePurhcaseCostAndMarketValue(Long totalPurchaseCost) {
        this.totalPurchaseCost = totalPurchaseCost;
        this.marketValue = totalPurchaseCost;
    }

    public Long getWeeklyRent() {
        return weeklyRent == null ? 0 : weeklyRent;
    }

    public void setWeeklyRent(Long weeklyRent) {
        this.weeklyRent = weeklyRent;
    }

    public void setLegalFees(Long legalFees) {
        this.legalFees = legalFees;
    }

    public Long getLegalFees() {
        return legalFees == null ? DEFAULT_LEGAL_FEES : legalFees;
    }

    public Long getBuildingInspectionFees() {
        return buildingInspectionFees == null ? 0 : buildingInspectionFees;
    }

    public void setBuildingInspectionFees(Long buildingInspectionFees) {
        this.buildingInspectionFees = buildingInspectionFees;
    }

    public Long getTitleRegistrationFees() {
        return titleRegistrationFees == null ? DEFAULT_TITLE_REGISTRATION_FEES
                : titleRegistrationFees;
    }

    public void setTitleRegistrationFees(Long titleRegistration) {
        this.titleRegistrationFees = titleRegistration;
    }

    public void setLoanApplicationFees(Long loanApplicationFee) {
        this.loanApplicationFees = loanApplicationFee;
    }

    public Long getLoanApplicationFees() {
        return loanApplicationFees == null ? 0 : loanApplicationFees;
    }

    public Long getMortgageInsurance() {
        return mortgageInsurance == null ? 0 : mortgageInsurance;
    }

    public void setMortgageInsurance(Long mortgageInsurance) {
        this.mortgageInsurance = mortgageInsurance;
    }

    public void setMortgageInsuranceStampDuty(Long mortgageInsuranceStampDuty) {
        this.mortgageInsuranceStampDuty = mortgageInsuranceStampDuty;
    }

    public Long getMortgageInsuranceStampDuty() {
        return mortgageInsuranceStampDuty == null ? 0
                : mortgageInsuranceStampDuty;
    }

    public void setMortgageStampDuty(Long mortgageStampDuty) {
        this.mortgageStampDuty = mortgageStampDuty;
    }

    public Long getMortgageStampDuty() {
        return mortgageStampDuty == null ? 0 : mortgageStampDuty;
    }

    public void setWeeksRented(Byte weeksRented) {
        this.weeksRented = weeksRented;
    }

    public Byte getWeeksRented() {
        return weeksRented == null ? DEFAULT_WEEKS_RENTED : weeksRented;
    }

    public Date getConstructionDate() {
        return constructionDate;
    }

    public void setConstructionDate(Date constructionDate) {
        this.constructionDate = constructionDate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestRate() {
        return interestRate == null ? DEFAULT_INTEREST_RATE : interestRate;
    }

    // public void setPropertyManagementFees(Double propertyManagementFees) {
    // this.propertyManagementFees = propertyManagementFees;
    // }
    //
    // public Double getPropertyManagementFees() {
    // return propertyManagementFees == null ? DEFAULT_PROPERTY_MANAGEMENT_FEES
    // : propertyManagementFees;
    // }

    public Date getPurchaseDate() {
        return purchaseDate == null ? new Date() : purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setBuildingValue(Long buildingValue) {
        this.buildingValue = buildingValue;
    }

    public Long getBuildingValue() {
        return buildingValue == null ? 0 : buildingValue;
    }

    public void setFittingsValue(Long fittingsValue) {
        this.fittingsValue = fittingsValue;
    }

    public Long getFittingsValue() {
        return fittingsValue == null ? 0 : fittingsValue;
    }

    public Long[] getBuyingCosts() {
        Long[] buyingCosts = new Long[] { getStampDuty(), getLegalFees(),
                getBuildingInspectionFees(), getTitleRegistrationFees(), };
        return buyingCosts;
    }

    public Long[] getBorrowingCosts() {
        Long[] borrowingCosts = new Long[] { getMortgageStampDuty(),
                getMortgageInsurance(), getMortgageInsuranceStampDuty(),
                getLoanApplicationFees(), };
        return borrowingCosts;
    }

    public Long[] getOngoingCosts() {
        return this.ongoingCosts.getOngoingCosts(getPropertyManagementFees());
    }

    public Long getPropertyManagementFees() {
        return MathUtil.doubleToLong(weeklyRent * getManagementFeeRate() / 100);
    }

    public Long totalBuyingCost() {
        return sum(getBuyingCosts());
    }

    public Long calculateTotalOngoingCost() {
        return sum(getOngoingCosts());
    }

    public Long totalBorrowingCost() {
        return sum(getBorrowingCosts());
    }

    // TODO: Revisit, where is this used?
    Long sum(Long[] costs) {
        long totalCost = 0;
        for (Long cost : costs) {
            totalCost += cost;
        }
        return totalCost;
    }

    public long calculateOwnerGrossIncome() {
        long totalAnnualIncome = 0;
        for (Owner owner : getOwnerList()) {
            totalAnnualIncome += owner.getAnnualIncome();
        }
        return totalAnnualIncome;
    }

    public long calculateOwnerTax() {
        long totalAnnualTax = 0;
        for (Owner owner : getOwnerList()) {
            totalAnnualTax += owner.getTax();
        }
        return totalAnnualTax;
    }

    public long calculateYearlyRent() {
        return weeklyRent * weeksRented;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return SerializationUtils.clone(this);
    }

    public void projectBy(ProjectionParameters projectionParameters) {
        this.setWeeklyRent(MathUtil.doubleToLong(MathUtil.increaseBy(
                this.getWeeklyRent(),
                projectionParameters.getRentIncreaseRate())));
        this.setMarketValue(MathUtil.doubleToLong(MathUtil.increaseBy(
                this.getMarketValue(),
                projectionParameters.getCapitalGrowthRate())));
        this.ongoingCosts.projectBy(projectionParameters);

        for (Owner owner : getOwnerList()) {
            owner.setAnnualIncome(MathUtil.doubleToLong(MathUtil.increaseBy(
                    owner.getAnnualIncome(),
                    projectionParameters.getSalaryIncreaseRate())));
        }
    }

    public Integer getLoanTerm() {
        return this.loanTerm == null ? DEFAULT_LOAN_TERM : this.loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public int getInterestOnlyPeriod() {
        return interestOnlyPeriod == null ? DEFAULT_INTEREST_ONLY_PERIOD
                : interestOnlyPeriod;
    }

    public void setInterestOnlyPeriod(int interestOnlyPeriod) {
        this.interestOnlyPeriod = interestOnlyPeriod;
    }

    public Long getCleaning() {
        return this.ongoingCosts.getCleaning();
    }

    public void setCleaning(Long cleaning) {
        this.ongoingCosts.setCleaning(cleaning);
    }

    public void setCouncilRates(Long councilRates) {
        this.ongoingCosts.setCouncilRates(councilRates);
    }

    public void setLandlordsInsurance(Long landlordsInsurance) {
        this.ongoingCosts.setLandlordsInsurance(landlordsInsurance);
    }

    public Long getLandlordsInsurance() {
        return this.ongoingCosts.getLandlordsInsurance();
    }

    public Long getMaintenance() {
        return this.ongoingCosts.getMaintenance();
    }

    public Long getStrata() {
        return this.ongoingCosts.getStrata();
    }

    public void setMaintenance(Long maintenance) {
        this.ongoingCosts.setMaintenance(maintenance);
    }

    public void setStrata(Long strata) {
        this.ongoingCosts.setStrata(strata);
    }

    public void setWaterCharges(Long waterCharges) {
        this.ongoingCosts.setWaterCharges(waterCharges);
    }

    public void setGardening(Long gardening) {
        this.ongoingCosts.setGardening(gardening);
    }

    public void setTaxExpenses(Long taxExpenses) {
        this.ongoingCosts.setTaxExpenses(taxExpenses);
    }

    public void setMiscOngoingExpenses(Long miscOngoingExpenses) {
        this.ongoingCosts.setMiscOngoingExpenses(miscOngoingExpenses);
    }

    public Long getMiscOngoingExpenses() {
        return this.ongoingCosts.getMiscOngoingExpenses();
    }

    public Long getWaterCharges() {
        return this.ongoingCosts.getWaterCharges();
    }

    public Long getCouncilRates() {
        return this.ongoingCosts.getCouncilRates();
    }

    public Long getGardening() {
        return this.ongoingCosts.getGardening();
    }

    public Long getTaxExpenses() {
        return this.ongoingCosts.getTaxExpenses();
    }

    public void setOngoingCosts(OngoingCosts ongoingCosts) {
        this.ongoingCosts = ongoingCosts;
    }

    public void setLvr(Double lvr) {
        this.lvr = lvr;
    }

    public Double getLvr() {
        return lvr;
    }

    public int getId() {
        return this.idProperty;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Double getManagementFeeRate() {
        return managementFeeRate == null ? DEFAULT_PROPERTY_MANAGEMENT_FEES
                : managementFeeRate;
    }

    public void setManagementFeeRate(Double managementFeeRate) {
        this.managementFeeRate = managementFeeRate;
    }

    public Long getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Long marketValue) {
        this.marketValue = marketValue;
    }
}
