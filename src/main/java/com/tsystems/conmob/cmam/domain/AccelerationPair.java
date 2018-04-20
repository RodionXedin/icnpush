package com.tsystems.conmob.cmam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AccelerationPair.
 */
@Entity
@Table(name = "acceleration_pair")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccelerationPair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "icn_id")
    private Integer icnId;

    @Column(name = "acc_x")
    private Integer accX;

    @Column(name = "acc_y")
    private Integer accY;

    @Column(name = "acc_z")
    private Integer accZ;

    @Column(name = "jhi_time")
    private Integer time;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIcnId() {
        return icnId;
    }

    public AccelerationPair icnId(Integer icnId) {
        this.icnId = icnId;
        return this;
    }

    public void setIcnId(Integer icnId) {
        this.icnId = icnId;
    }

    public Integer getAccX() {
        return accX;
    }

    public AccelerationPair accX(Integer accX) {
        this.accX = accX;
        return this;
    }

    public void setAccX(Integer accX) {
        this.accX = accX;
    }

    public Integer getAccY() {
        return accY;
    }

    public AccelerationPair accY(Integer accY) {
        this.accY = accY;
        return this;
    }

    public void setAccY(Integer accY) {
        this.accY = accY;
    }

    public Integer getAccZ() {
        return accZ;
    }

    public AccelerationPair accZ(Integer accZ) {
        this.accZ = accZ;
        return this;
    }

    public void setAccZ(Integer accZ) {
        this.accZ = accZ;
    }

    public Integer getTime() {
        return time;
    }

    public AccelerationPair time(Integer time) {
        this.time = time;
        return this;
    }

    public void setTime(Integer time) {
        this.time = time;
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
        AccelerationPair accelerationPair = (AccelerationPair) o;
        if (accelerationPair.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accelerationPair.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccelerationPair{" +
            "id=" + getId() +
            ", icnId=" + getIcnId() +
            ", accX=" + getAccX() +
            ", accY=" + getAccY() +
            ", accZ=" + getAccZ() +
            ", time=" + getTime() +
            "}";
    }
}
