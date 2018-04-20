package com.tsystems.conmob.cmam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SpeedData.
 */
@Entity
@Table(name = "speed_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpeedData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "icn_id")
    private Integer icnId;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "altitude")
    private Integer altitude;

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

    public SpeedData icnId(Integer icnId) {
        this.icnId = icnId;
        return this;
    }

    public void setIcnId(Integer icnId) {
        this.icnId = icnId;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public SpeedData latitude(Integer latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public SpeedData longitude(Integer longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public SpeedData speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public SpeedData altitude(Integer altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getTime() {
        return time;
    }

    public SpeedData time(Integer time) {
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
        SpeedData speedData = (SpeedData) o;
        if (speedData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), speedData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpeedData{" +
            "id=" + getId() +
            ", icnId=" + getIcnId() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", speed=" + getSpeed() +
            ", altitude=" + getAltitude() +
            ", time=" + getTime() +
            "}";
    }
}
