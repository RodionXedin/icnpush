package com.tsystems.conmob.cmam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ICNPush.
 */
@Entity
@Table(name = "icn_push")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ICNPush implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "esn")
    private String esn;

    @Column(name = "datetime")
    private String datetime;

    @OneToOne
    @JoinColumn(unique = true)
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(unique = true)
    private Crash crash;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsn() {
        return esn;
    }

    public ICNPush esn(String esn) {
        this.esn = esn;
        return this;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public String getDatetime() {
        return datetime;
    }

    public ICNPush datetime(String datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ICNPush vehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Crash getCrash() {
        return crash;
    }

    public ICNPush crash(Crash crash) {
        this.crash = crash;
        return this;
    }

    public void setCrash(Crash crash) {
        this.crash = crash;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ICNPush iCNPush = (ICNPush) o;
        if (iCNPush.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iCNPush.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ICNPush{" +
            "id=" + getId() +
            ", esn='" + getEsn() + "'" +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
