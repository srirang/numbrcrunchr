package com.numbrcrunchr.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author AMIS005
 * 
 *         Holds one stamp duty rate for a given lower and upper limit
 * 
 *         For example: New South Wales $0 < $14,000 1.25% of dutiable value
 *         $14,001 - $30,000 $175 + 1.5% of dutiable value $30,001 - $80,000
 *         $415 + 1.75% of dutiable value $80,001 - $300,000 $1,290 + 3.5% of
 *         dutiable value $300,001 - $1 million $8,990 + 4.5% of dutiable value
 *         > $1 million $40,490 + 5.5% of dutiable value
 */
@Entity
@Table(name = "Stamp_Duty_Rates")
public class StampDutyRate extends AbstractRate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Required for JPA
     */
    public StampDutyRate() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStamp_Duty_Rates", nullable = false)
    private Integer id;
    @Column(name = "state_code", length = 45)
    private String state;

    public StampDutyRate(String state, Long lowerLimit, Long upperLimit,
            long flatRate, double percentage) {
        super(lowerLimit, upperLimit, flatRate, percentage);
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state + "\t" + super.toString();
    }

    public String getState() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StampDutyRate)) {
            return false;
        }
        StampDutyRate other = (StampDutyRate) object;
        if (this.id == null && other.id != null || this.id != null
                && !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    protected void setId(Integer id) {
        this.id = id;
    }
}
