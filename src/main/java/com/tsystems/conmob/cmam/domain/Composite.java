package com.tsystems.conmob.cmam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Composite.
 */
@Entity
@Table(name = "composite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Composite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "crash_date")
    private String crashDate;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "odometer")
    private Integer odometer;

    @Column(name = "heading")
    private Integer heading;

    @Column(name = "raw_data")
    private String rawData;

    @Column(name = "altitude")
    private Integer altitude;

    @Column(name = "number_of_satellites")
    private Integer numberOfSatellites;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @Column(name = "rssi")
    private Integer rssi;

    @Column(name = "ntf_eng_request_id")
    private String ntfEngRequestId;

    @Column(name = "crash_uuid")
    private String crashUuid;

    @Column(name = "device_id_type")
    private String deviceIdType;

    @Column(name = "crush")
    private Integer crush;

    @Column(name = "pdof")
    private Integer pdof;

    @Column(name = "vin")
    private String vin;

    @Column(name = "severity_processed_on")
    private String severityProcessedOn;

    @Column(name = "severity_code")
    private String severityCode;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "jhi_year")
    private String year;

    @Column(name = "vin_region")
    private String vinRegion;

    @Column(name = "mpd_2")
    private Integer mpd2;

    @Column(name = "mpd_3")
    private Integer mpd3;

    @Column(name = "peak_g")
    private Integer peakG;

    @Column(name = "delta_v")
    private Integer deltaV;

    @Column(name = "vehicle_class")
    private String vehicleClass;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "last_modified_on")
    private String lastModifiedOn;

    @Column(name = "street_1")
    private String street1;

    @Column(name = "street_2")
    private String street2;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "crash_direction")
    private String crashDirection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Composite deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCrashDate() {
        return crashDate;
    }

    public Composite crashDate(String crashDate) {
        this.crashDate = crashDate;
        return this;
    }

    public void setCrashDate(String crashDate) {
        this.crashDate = crashDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Composite latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Composite longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Composite speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public Composite odometer(Integer odometer) {
        this.odometer = odometer;
        return this;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public Integer getHeading() {
        return heading;
    }

    public Composite heading(Integer heading) {
        this.heading = heading;
        return this;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public String getRawData() {
        return rawData;
    }

    public Composite rawData(String rawData) {
        this.rawData = rawData;
        return this;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public Composite altitude(Integer altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getNumberOfSatellites() {
        return numberOfSatellites;
    }

    public Composite numberOfSatellites(Integer numberOfSatellites) {
        this.numberOfSatellites = numberOfSatellites;
        return this;
    }

    public void setNumberOfSatellites(Integer numberOfSatellites) {
        this.numberOfSatellites = numberOfSatellites;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public Composite sequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getRssi() {
        return rssi;
    }

    public Composite rssi(Integer rssi) {
        this.rssi = rssi;
        return this;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public String getNtfEngRequestId() {
        return ntfEngRequestId;
    }

    public Composite ntfEngRequestId(String ntfEngRequestId) {
        this.ntfEngRequestId = ntfEngRequestId;
        return this;
    }

    public void setNtfEngRequestId(String ntfEngRequestId) {
        this.ntfEngRequestId = ntfEngRequestId;
    }

    public String getCrashUuid() {
        return crashUuid;
    }

    public Composite crashUuid(String crashUuid) {
        this.crashUuid = crashUuid;
        return this;
    }

    public void setCrashUuid(String crashUuid) {
        this.crashUuid = crashUuid;
    }

    public String getDeviceIdType() {
        return deviceIdType;
    }

    public Composite deviceIdType(String deviceIdType) {
        this.deviceIdType = deviceIdType;
        return this;
    }

    public void setDeviceIdType(String deviceIdType) {
        this.deviceIdType = deviceIdType;
    }

    public Integer getCrush() {
        return crush;
    }

    public Composite crush(Integer crush) {
        this.crush = crush;
        return this;
    }

    public void setCrush(Integer crush) {
        this.crush = crush;
    }

    public Integer getPdof() {
        return pdof;
    }

    public Composite pdof(Integer pdof) {
        this.pdof = pdof;
        return this;
    }

    public void setPdof(Integer pdof) {
        this.pdof = pdof;
    }

    public String getVin() {
        return vin;
    }

    public Composite vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSeverityProcessedOn() {
        return severityProcessedOn;
    }

    public Composite severityProcessedOn(String severityProcessedOn) {
        this.severityProcessedOn = severityProcessedOn;
        return this;
    }

    public void setSeverityProcessedOn(String severityProcessedOn) {
        this.severityProcessedOn = severityProcessedOn;
    }

    public String getSeverityCode() {
        return severityCode;
    }

    public Composite severityCode(String severityCode) {
        this.severityCode = severityCode;
        return this;
    }

    public void setSeverityCode(String severityCode) {
        this.severityCode = severityCode;
    }

    public String getMake() {
        return make;
    }

    public Composite make(String make) {
        this.make = make;
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public Composite model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public Composite year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVinRegion() {
        return vinRegion;
    }

    public Composite vinRegion(String vinRegion) {
        this.vinRegion = vinRegion;
        return this;
    }

    public void setVinRegion(String vinRegion) {
        this.vinRegion = vinRegion;
    }

    public Integer getMpd2() {
        return mpd2;
    }

    public Composite mpd2(Integer mpd2) {
        this.mpd2 = mpd2;
        return this;
    }

    public void setMpd2(Integer mpd2) {
        this.mpd2 = mpd2;
    }

    public Integer getMpd3() {
        return mpd3;
    }

    public Composite mpd3(Integer mpd3) {
        this.mpd3 = mpd3;
        return this;
    }

    public void setMpd3(Integer mpd3) {
        this.mpd3 = mpd3;
    }

    public Integer getPeakG() {
        return peakG;
    }

    public Composite peakG(Integer peakG) {
        this.peakG = peakG;
        return this;
    }

    public void setPeakG(Integer peakG) {
        this.peakG = peakG;
    }

    public Integer getDeltaV() {
        return deltaV;
    }

    public Composite deltaV(Integer deltaV) {
        this.deltaV = deltaV;
        return this;
    }

    public void setDeltaV(Integer deltaV) {
        this.deltaV = deltaV;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public Composite vehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
        return this;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public Composite createdOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public Composite lastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
        return this;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getStreet1() {
        return street1;
    }

    public Composite street1(String street1) {
        this.street1 = street1;
        return this;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public Composite street2(String street2) {
        this.street2 = street2;
        return this;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getZip() {
        return zip;
    }

    public Composite zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public Composite country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Composite city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Composite stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCrashDirection() {
        return crashDirection;
    }

    public Composite crashDirection(String crashDirection) {
        this.crashDirection = crashDirection;
        return this;
    }

    public void setCrashDirection(String crashDirection) {
        this.crashDirection = crashDirection;
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
        Composite composite = (Composite) o;
        if (composite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), composite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Composite{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", crashDate='" + getCrashDate() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", speed=" + getSpeed() +
            ", odometer=" + getOdometer() +
            ", heading=" + getHeading() +
            ", rawData='" + getRawData() + "'" +
            ", altitude=" + getAltitude() +
            ", numberOfSatellites=" + getNumberOfSatellites() +
            ", sequenceNumber=" + getSequenceNumber() +
            ", rssi=" + getRssi() +
            ", ntfEngRequestId='" + getNtfEngRequestId() + "'" +
            ", crashUuid='" + getCrashUuid() + "'" +
            ", deviceIdType='" + getDeviceIdType() + "'" +
            ", crush=" + getCrush() +
            ", pdof=" + getPdof() +
            ", vin='" + getVin() + "'" +
            ", severityProcessedOn='" + getSeverityProcessedOn() + "'" +
            ", severityCode='" + getSeverityCode() + "'" +
            ", make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", year='" + getYear() + "'" +
            ", vinRegion='" + getVinRegion() + "'" +
            ", mpd2=" + getMpd2() +
            ", mpd3=" + getMpd3() +
            ", peakG=" + getPeakG() +
            ", deltaV=" + getDeltaV() +
            ", vehicleClass='" + getVehicleClass() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastModifiedOn='" + getLastModifiedOn() + "'" +
            ", street1='" + getStreet1() + "'" +
            ", street2='" + getStreet2() + "'" +
            ", zip='" + getZip() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", crashDirection='" + getCrashDirection() + "'" +
            "}";
    }
}
