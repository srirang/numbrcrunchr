package au.com.numbrcrunchr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author AMIS005
 */
@Entity
@Table(name = "owner")
@NamedQueries({
        @NamedQuery(name = "Owner.findAll", query = "SELECT o FROM Owner o"),
        @NamedQuery(name = "Owner.findByIdOwner", query = "SELECT o FROM Owner o WHERE o.idOwner = :idOwner"),
        @NamedQuery(name = "Owner.findByAnnualIncome", query = "SELECT o FROM Owner o WHERE o.annualIncome = :annualIncome"),
        @NamedQuery(name = "Owner.findByOwnershipShare", query = "SELECT o FROM Owner o WHERE o.ownershipShare = :ownershipShare") })
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final double DEFAULT_ANNUAL_INCOME = 100000;
    private static final Double DEFAULT_OWNERSHIP_SHARE = 100.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOwner", nullable = false)
    private Integer idOwner;
    @Column(name = "annual_income")
    private double annualIncome = DEFAULT_ANNUAL_INCOME;
    @Column(name = "ownership_share", precision = 22)
    private Double ownershipShare = DEFAULT_OWNERSHIP_SHARE;
    @JoinTable(name = "property_has_owner", joinColumns = { @JoinColumn(name = "Owner_idOwner", referencedColumnName = "idOwner", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "Property_idProperty", referencedColumnName = "idProperty", nullable = false) })
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Property> propertyList;
    private double tax;
    private Boolean medicareLevyApplies = Boolean.TRUE;

    public Owner() {
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += idOwner != null ? idOwner.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        if (this.idOwner == null && other.idOwner != null
                || this.idOwner != null && !this.idOwner.equals(other.idOwner)) {
            return false;
        }
        return true;
    }

    public void addProperty(Property property) {
        if (this.propertyList == null) {
            this.propertyList = new ArrayList<Property>();
        }
        this.propertyList.add(property);
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setMedicareLevyApplies(Boolean medicareLevyApplies) {
        this.medicareLevyApplies = medicareLevyApplies;
    }

    // Default value is true
    public Boolean getMedicareLevyApplies() {
        return medicareLevyApplies == null ? true : medicareLevyApplies;
    }

    public static Owner createOwner(long annualIncome) {
        Owner owner = new Owner();
        owner.setAnnualIncome(annualIncome);
        return owner;
    }

    public void setOwnershipShare(Double ownershipShare) {
        this.ownershipShare = ownershipShare;
    }

    public Double getOwnershipShare() {
        return ownershipShare;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
