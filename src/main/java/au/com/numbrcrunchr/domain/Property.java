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
import org.apache.commons.lang.builder.ToStringStyle;

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
    private static final double DEFAULT_TITLE_REGISTRATION_FEES = 300l;
    private static final double DEFAULT_LEGAL_FEES = 800l;

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
    private double totalPurchaseCost;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "deposit")
    private double deposit;

    @Column(name = "loan_amount")
    private double loanAmount;

    @Column(name = "weekly_rent")
    private double weeklyRent;

    @Column(name = "management_fee_rate")
    private Double managementFeeRate = DEFAULT_PROPERTY_MANAGEMENT_FEES;

    @Basic(optional = false)
    @Column(name = "state", nullable = false, length = 45)
    private String state;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @ManyToMany(mappedBy = "propertyList", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Owner> ownerList;

    // Buying costs
    @Column(name = "stamp_duty")
    private double stampDuty;

    @Column(name = "legal_fees")
    private double legalFees = DEFAULT_LEGAL_FEES;

    @Column(name = "interest_only_period")
    private Integer interestOnlyPeriod = DEFAULT_INTEREST_ONLY_PERIOD;

    @Column(name = "building_inspection_fees")
    private double buildingInspectionFees;

    @Column(name = "title_registration_fees")
    private double titleRegistrationFees = DEFAULT_TITLE_REGISTRATION_FEES;

    // Borrowing costs
    @Column(name = "mortgage_stamp_duty")
    private double mortgageStampDuty;

    @Column(name = "mortgage_insurance")
    private double mortgageInsurance;

    @Column(name = "mortgage_insurance_stamp_duty")
    private double mortgageInsuranceStampDuty;

    @Column(name = "loan_application_fees")
    private double loanApplicationFees;

    @Embedded
    private OngoingCosts ongoingCosts = new OngoingCosts();

    // Assumptions
    @Column(name = "interest_rate")
    private Double interestRate = DEFAULT_INTEREST_RATE;

    @Column(name = "lvr")
    private Double lvr = DEFAULT_LVR;

    @Column(name = "market_value")
    private double marketValue;

    @Column(name = "construction_date")
    @Temporal(TemporalType.DATE)
    private Date constructionDate;

    @Column(name = "building_value")
    private double buildingValue;

    @Column(name = "fittings_value")
    private double fittingsValue;

    @Column(name = "weeks_rented")
    private Byte weeksRented = DEFAULT_WEEKS_RENTED;

    @Column(name = "loan_term")
    private Integer loanTerm = DEFAULT_LOAN_TERM;

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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
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
        hash += idProperty != null ? idProperty.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if (this.idProperty == null && other.idProperty != null
                || this.idProperty != null
                && !this.idProperty.equals(other.idProperty)) {
            return false;
        }
        return true;
    }

    public double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(double stampDuty) {
        this.stampDuty = stampDuty;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getTotalPurchaseCost() {
        return totalPurchaseCost;
    }

    public void initialisePurhcaseCostAndMarketValue(double totalPurchaseCost) {
        this.totalPurchaseCost = totalPurchaseCost;
        this.marketValue = totalPurchaseCost;
    }

    public double getWeeklyRent() {
        return weeklyRent;
    }

    public void setWeeklyRent(double weeklyRent) {
        this.weeklyRent = weeklyRent;
    }

    public void setLegalFees(double legalFees) {
        this.legalFees = legalFees;
    }

    public double getLegalFees() {
        return legalFees;
    }

    public double getBuildingInspectionFees() {
        return buildingInspectionFees;
    }

    public void setBuildingInspectionFees(double buildingInspectionFees) {
        this.buildingInspectionFees = buildingInspectionFees;
    }

    public double getTitleRegistrationFees() {
        return titleRegistrationFees;
    }

    public void setTitleRegistrationFees(double titleRegistration) {
        this.titleRegistrationFees = titleRegistration;
    }

    public void setLoanApplicationFees(double loanApplicationFee) {
        this.loanApplicationFees = loanApplicationFee;
    }

    public double getLoanApplicationFees() {
        return loanApplicationFees;
    }

    public double getMortgageInsurance() {
        return mortgageInsurance;
    }

    public void setMortgageInsurance(double mortgageInsurance) {
        this.mortgageInsurance = mortgageInsurance;
    }

    public void setMortgageInsuranceStampDuty(double mortgageInsuranceStampDuty) {
        this.mortgageInsuranceStampDuty = mortgageInsuranceStampDuty;
    }

    public double getMortgageInsuranceStampDuty() {
        return mortgageInsuranceStampDuty;
    }

    public void setMortgageStampDuty(double mortgageStampDuty) {
        this.mortgageStampDuty = mortgageStampDuty;
    }

    public double getMortgageStampDuty() {
        return mortgageStampDuty;
    }

    public void setWeeksRented(Byte weeksRented) {
        this.weeksRented = weeksRented;
    }

    public Byte getWeeksRented() {
        return weeksRented;
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
        return interestRate;
    }

    public Date getPurchaseDate() {
        return purchaseDate == null ? new Date() : purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setBuildingValue(double buildingValue) {
        this.buildingValue = buildingValue;
    }

    public double getBuildingValue() {
        return buildingValue;
    }

    public void setFittingsValue(double fittingsValue) {
        this.fittingsValue = fittingsValue;
    }

    public double getFittingsValue() {
        return fittingsValue;
    }

    public Double[] getBuyingCosts() {
        Double[] buyingCosts = new Double[] { getStampDuty(), getLegalFees(),
                getBuildingInspectionFees(), getTitleRegistrationFees(), };
        return buyingCosts;
    }

    public Double[] getBorrowingCosts() {
        Double[] borrowingCosts = new Double[] { getMortgageStampDuty(),
                getMortgageInsurance(), getMortgageInsuranceStampDuty(),
                getLoanApplicationFees(), };
        return borrowingCosts;
    }

    public double getTotalOngoingCosts() {
        return this.ongoingCosts
                .getTotalOngoingCosts(getPropertyManagementFees());
    }

    public double getPropertyManagementFees() {
        return weeklyRent * getManagementFeeRate() / 100 * weeksRented;
    }

    public double calculateOwnerGrossIncome() {
        double totalAnnualIncome = 0;
        for (Owner owner : getOwnerList()) {
            totalAnnualIncome += owner.getAnnualIncome();
        }
        return totalAnnualIncome;
    }

    public double calculateOwnerTax() {
        double totalAnnualTax = 0;
        for (Owner owner : getOwnerList()) {
            totalAnnualTax += owner.getTax();
        }
        return totalAnnualTax;
    }

    public double calculateYearlyRent() {
        return weeklyRent * weeksRented;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return SerializationUtils.clone(this);
    }

    public void projectBy(ProjectionParameters projectionParameters) {
        this.setWeeklyRent(MathUtil.increaseBy(this.getWeeklyRent(),
                projectionParameters.getRentIncreaseRate()));
        this.setMarketValue(MathUtil.increaseBy(this.getMarketValue(),
                projectionParameters.getCapitalGrowthRate()));
        this.ongoingCosts.projectBy(projectionParameters.getCpi());

        for (Owner owner : getOwnerList()) {
            owner.setAnnualIncome(MathUtil.increaseBy(owner.getAnnualIncome(),
                    projectionParameters.getSalaryIncreaseRate()));
        }
    }

    public Integer getLoanTerm() {
        return this.loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public int getInterestOnlyPeriod() {
        return interestOnlyPeriod;
    }

    public void setInterestOnlyPeriod(int interestOnlyPeriod) {
        this.interestOnlyPeriod = interestOnlyPeriod;
    }

    public double getCleaning() {
        return this.ongoingCosts.getCleaning();
    }

    public void setCleaning(double cleaning) {
        this.ongoingCosts.setCleaning(cleaning);
    }

    public void setCouncilRates(double councilRates) {
        this.ongoingCosts.setCouncilRates(councilRates);
    }

    public void setLandlordsInsurance(double landlordsInsurance) {
        this.ongoingCosts.setLandlordsInsurance(landlordsInsurance);
    }

    public double getLandlordsInsurance() {
        return this.ongoingCosts.getLandlordsInsurance();
    }

    public double getMaintenance() {
        return this.ongoingCosts.getMaintenance();
    }

    public double getStrata() {
        return this.ongoingCosts.getStrata();
    }

    public void setMaintenance(double maintenance) {
        this.ongoingCosts.setMaintenance(maintenance);
    }

    public void setStrata(double strata) {
        this.ongoingCosts.setStrata(strata);
    }

    public void setWaterCharges(double waterCharges) {
        this.ongoingCosts.setWaterCharges(waterCharges);
    }

    public void setGardening(double gardening) {
        this.ongoingCosts.setGardening(gardening);
    }

    public void setTaxExpenses(double taxExpenses) {
        this.ongoingCosts.setTaxExpenses(taxExpenses);
    }

    public void setMiscOngoingExpenses(double miscOngoingExpenses) {
        this.ongoingCosts.setMiscOngoingExpenses(miscOngoingExpenses);
    }

    public double getMiscOngoingExpenses() {
        return this.ongoingCosts.getMiscOngoingExpenses();
    }

    public double getWaterCharges() {
        return this.ongoingCosts.getWaterCharges();
    }

    public double getCouncilRates() {
        return this.ongoingCosts.getCouncilRates();
    }

    public double getGardening() {
        return this.ongoingCosts.getGardening();
    }

    public double getTaxExpenses() {
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
        return managementFeeRate;
    }

    public void setManagementFeeRate(Double managementFeeRate) {
        this.managementFeeRate = managementFeeRate;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public double getGovermentCosts() {
        return this.getStampDuty() + this.getLegalFees()
                + this.getMortgageInsuranceStampDuty()
                + this.getMortgageStampDuty();
    }
}
