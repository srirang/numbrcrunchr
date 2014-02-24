package au.com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Payg_Tax_Rates")
public class TaxRate extends AbstractRate implements Serializable {

    private static final long serialVersionUID = 1L;

    public TaxRate() {
    }

    public TaxRate(Long lowerLimit, Long upperLimit, long flatRate,
            double percentage) {
        super(lowerLimit, upperLimit, flatRate, percentage);
    }

    public TaxRate(String taxYear, Long lowerLimit, Long upperLimit,
            long flatRate, double percentage) {
        super(lowerLimit, upperLimit, flatRate, percentage);
        this.taxYear = taxYear;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPayg_Tax_Rates", nullable = false)
    private Integer id;

    @Column(name = "tax_year", length = 45)
    private String taxYear;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TaxRate)) {
            return false;
        }
        TaxRate other = (TaxRate) object;
        if (this.id == null && other.id != null || this.id != null
                && !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
