package com.tsystems.conmob.cmam.web.rest;

import com.tsystems.conmob.cmam.IcnpushApp;

import com.tsystems.conmob.cmam.domain.Composite;
import com.tsystems.conmob.cmam.repository.CompositeRepository;
import com.tsystems.conmob.cmam.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tsystems.conmob.cmam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompositeResource REST controller.
 *
 * @see CompositeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcnpushApp.class)
public class CompositeResourceIntTest {

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CRASH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CRASH_DATE = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer UPDATED_SPEED = 2;

    private static final Integer DEFAULT_ODOMETER = 1;
    private static final Integer UPDATED_ODOMETER = 2;

    private static final Integer DEFAULT_HEADING = 1;
    private static final Integer UPDATED_HEADING = 2;

    private static final String DEFAULT_RAW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RAW_DATA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTITUDE = 1;
    private static final Integer UPDATED_ALTITUDE = 2;

    private static final Integer DEFAULT_NUMBER_OF_SATELLITES = 1;
    private static final Integer UPDATED_NUMBER_OF_SATELLITES = 2;

    private static final Integer DEFAULT_SEQUENCE_NUMBER = 1;
    private static final Integer UPDATED_SEQUENCE_NUMBER = 2;

    private static final Integer DEFAULT_RSSI = 1;
    private static final Integer UPDATED_RSSI = 2;

    private static final String DEFAULT_NTF_ENG_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_NTF_ENG_REQUEST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CRASH_UUID = "AAAAAAAAAA";
    private static final String UPDATED_CRASH_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CRUSH = 1;
    private static final Integer UPDATED_CRUSH = 2;

    private static final Integer DEFAULT_PDOF = 1;
    private static final Integer UPDATED_PDOF = 2;

    private static final String DEFAULT_VIN = "AAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBB";

    private static final String DEFAULT_SEVERITY_PROCESSED_ON = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY_PROCESSED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_SEVERITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_VIN_REGION = "AAAAAAAAAA";
    private static final String UPDATED_VIN_REGION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MPD_2 = 1;
    private static final Integer UPDATED_MPD_2 = 2;

    private static final Integer DEFAULT_MPD_3 = 1;
    private static final Integer UPDATED_MPD_3 = 2;

    private static final Integer DEFAULT_PEAK_G = 1;
    private static final Integer UPDATED_PEAK_G = 2;

    private static final Integer DEFAULT_DELTA_V = 1;
    private static final Integer UPDATED_DELTA_V = 2;

    private static final String DEFAULT_VEHICLE_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_ON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_1 = "AAAAAAAAAA";
    private static final String UPDATED_STREET_1 = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_2 = "AAAAAAAAAA";
    private static final String UPDATED_STREET_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CRASH_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_CRASH_DIRECTION = "BBBBBBBBBB";

    @Autowired
    private CompositeRepository compositeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompositeMockMvc;

    private Composite composite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompositeResource compositeResource = new CompositeResource(compositeRepository);
        this.restCompositeMockMvc = MockMvcBuilders.standaloneSetup(compositeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composite createEntity(EntityManager em) {
        Composite composite = new Composite()
            .deviceId(DEFAULT_DEVICE_ID)
            .crashDate(DEFAULT_CRASH_DATE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .speed(DEFAULT_SPEED)
            .odometer(DEFAULT_ODOMETER)
            .heading(DEFAULT_HEADING)
            .rawData(DEFAULT_RAW_DATA)
            .altitude(DEFAULT_ALTITUDE)
            .numberOfSatellites(DEFAULT_NUMBER_OF_SATELLITES)
            .sequenceNumber(DEFAULT_SEQUENCE_NUMBER)
            .rssi(DEFAULT_RSSI)
            .ntfEngRequestId(DEFAULT_NTF_ENG_REQUEST_ID)
            .crashUuid(DEFAULT_CRASH_UUID)
            .deviceIdType(DEFAULT_DEVICE_ID_TYPE)
            .crush(DEFAULT_CRUSH)
            .pdof(DEFAULT_PDOF)
            .vin(DEFAULT_VIN)
            .severityProcessedOn(DEFAULT_SEVERITY_PROCESSED_ON)
            .severityCode(DEFAULT_SEVERITY_CODE)
            .make(DEFAULT_MAKE)
            .model(DEFAULT_MODEL)
            .year(DEFAULT_YEAR)
            .vinRegion(DEFAULT_VIN_REGION)
            .mpd2(DEFAULT_MPD_2)
            .mpd3(DEFAULT_MPD_3)
            .peakG(DEFAULT_PEAK_G)
            .deltaV(DEFAULT_DELTA_V)
            .vehicleClass(DEFAULT_VEHICLE_CLASS)
            .createdOn(DEFAULT_CREATED_ON)
            .lastModifiedOn(DEFAULT_LAST_MODIFIED_ON)
            .street1(DEFAULT_STREET_1)
            .street2(DEFAULT_STREET_2)
            .zip(DEFAULT_ZIP)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .crashDirection(DEFAULT_CRASH_DIRECTION);
        return composite;
    }

    @Before
    public void initTest() {
        composite = createEntity(em);
    }

    @Test
    @Transactional
    public void createComposite() throws Exception {
        int databaseSizeBeforeCreate = compositeRepository.findAll().size();

        // Create the Composite
        restCompositeMockMvc.perform(post("/api/composites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(composite)))
            .andExpect(status().isCreated());

        // Validate the Composite in the database
        List<Composite> compositeList = compositeRepository.findAll();
        assertThat(compositeList).hasSize(databaseSizeBeforeCreate + 1);
        Composite testComposite = compositeList.get(compositeList.size() - 1);
        assertThat(testComposite.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testComposite.getCrashDate()).isEqualTo(DEFAULT_CRASH_DATE);
        assertThat(testComposite.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testComposite.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testComposite.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testComposite.getOdometer()).isEqualTo(DEFAULT_ODOMETER);
        assertThat(testComposite.getHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testComposite.getRawData()).isEqualTo(DEFAULT_RAW_DATA);
        assertThat(testComposite.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testComposite.getNumberOfSatellites()).isEqualTo(DEFAULT_NUMBER_OF_SATELLITES);
        assertThat(testComposite.getSequenceNumber()).isEqualTo(DEFAULT_SEQUENCE_NUMBER);
        assertThat(testComposite.getRssi()).isEqualTo(DEFAULT_RSSI);
        assertThat(testComposite.getNtfEngRequestId()).isEqualTo(DEFAULT_NTF_ENG_REQUEST_ID);
        assertThat(testComposite.getCrashUuid()).isEqualTo(DEFAULT_CRASH_UUID);
        assertThat(testComposite.getDeviceIdType()).isEqualTo(DEFAULT_DEVICE_ID_TYPE);
        assertThat(testComposite.getCrush()).isEqualTo(DEFAULT_CRUSH);
        assertThat(testComposite.getPdof()).isEqualTo(DEFAULT_PDOF);
        assertThat(testComposite.getVin()).isEqualTo(DEFAULT_VIN);
        assertThat(testComposite.getSeverityProcessedOn()).isEqualTo(DEFAULT_SEVERITY_PROCESSED_ON);
        assertThat(testComposite.getSeverityCode()).isEqualTo(DEFAULT_SEVERITY_CODE);
        assertThat(testComposite.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testComposite.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testComposite.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testComposite.getVinRegion()).isEqualTo(DEFAULT_VIN_REGION);
        assertThat(testComposite.getMpd2()).isEqualTo(DEFAULT_MPD_2);
        assertThat(testComposite.getMpd3()).isEqualTo(DEFAULT_MPD_3);
        assertThat(testComposite.getPeakG()).isEqualTo(DEFAULT_PEAK_G);
        assertThat(testComposite.getDeltaV()).isEqualTo(DEFAULT_DELTA_V);
        assertThat(testComposite.getVehicleClass()).isEqualTo(DEFAULT_VEHICLE_CLASS);
        assertThat(testComposite.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testComposite.getLastModifiedOn()).isEqualTo(DEFAULT_LAST_MODIFIED_ON);
        assertThat(testComposite.getStreet1()).isEqualTo(DEFAULT_STREET_1);
        assertThat(testComposite.getStreet2()).isEqualTo(DEFAULT_STREET_2);
        assertThat(testComposite.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testComposite.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testComposite.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testComposite.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testComposite.getCrashDirection()).isEqualTo(DEFAULT_CRASH_DIRECTION);
    }

    @Test
    @Transactional
    public void createCompositeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compositeRepository.findAll().size();

        // Create the Composite with an existing ID
        composite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompositeMockMvc.perform(post("/api/composites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(composite)))
            .andExpect(status().isBadRequest());

        // Validate the Composite in the database
        List<Composite> compositeList = compositeRepository.findAll();
        assertThat(compositeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComposites() throws Exception {
        // Initialize the database
        compositeRepository.saveAndFlush(composite);

        // Get all the compositeList
        restCompositeMockMvc.perform(get("/api/composites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composite.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].crashDate").value(hasItem(DEFAULT_CRASH_DATE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)))
            .andExpect(jsonPath("$.[*].odometer").value(hasItem(DEFAULT_ODOMETER)))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING)))
            .andExpect(jsonPath("$.[*].rawData").value(hasItem(DEFAULT_RAW_DATA.toString())))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE)))
            .andExpect(jsonPath("$.[*].numberOfSatellites").value(hasItem(DEFAULT_NUMBER_OF_SATELLITES)))
            .andExpect(jsonPath("$.[*].sequenceNumber").value(hasItem(DEFAULT_SEQUENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].rssi").value(hasItem(DEFAULT_RSSI)))
            .andExpect(jsonPath("$.[*].ntfEngRequestId").value(hasItem(DEFAULT_NTF_ENG_REQUEST_ID.toString())))
            .andExpect(jsonPath("$.[*].crashUuid").value(hasItem(DEFAULT_CRASH_UUID.toString())))
            .andExpect(jsonPath("$.[*].deviceIdType").value(hasItem(DEFAULT_DEVICE_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].crush").value(hasItem(DEFAULT_CRUSH)))
            .andExpect(jsonPath("$.[*].pdof").value(hasItem(DEFAULT_PDOF)))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN.toString())))
            .andExpect(jsonPath("$.[*].severityProcessedOn").value(hasItem(DEFAULT_SEVERITY_PROCESSED_ON.toString())))
            .andExpect(jsonPath("$.[*].severityCode").value(hasItem(DEFAULT_SEVERITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].vinRegion").value(hasItem(DEFAULT_VIN_REGION.toString())))
            .andExpect(jsonPath("$.[*].mpd2").value(hasItem(DEFAULT_MPD_2)))
            .andExpect(jsonPath("$.[*].mpd3").value(hasItem(DEFAULT_MPD_3)))
            .andExpect(jsonPath("$.[*].peakG").value(hasItem(DEFAULT_PEAK_G)))
            .andExpect(jsonPath("$.[*].deltaV").value(hasItem(DEFAULT_DELTA_V)))
            .andExpect(jsonPath("$.[*].vehicleClass").value(hasItem(DEFAULT_VEHICLE_CLASS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedOn").value(hasItem(DEFAULT_LAST_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].street1").value(hasItem(DEFAULT_STREET_1.toString())))
            .andExpect(jsonPath("$.[*].street2").value(hasItem(DEFAULT_STREET_2.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].crashDirection").value(hasItem(DEFAULT_CRASH_DIRECTION.toString())));
    }

    @Test
    @Transactional
    public void getComposite() throws Exception {
        // Initialize the database
        compositeRepository.saveAndFlush(composite);

        // Get the composite
        restCompositeMockMvc.perform(get("/api/composites/{id}", composite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(composite.getId().intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID.toString()))
            .andExpect(jsonPath("$.crashDate").value(DEFAULT_CRASH_DATE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED))
            .andExpect(jsonPath("$.odometer").value(DEFAULT_ODOMETER))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING))
            .andExpect(jsonPath("$.rawData").value(DEFAULT_RAW_DATA.toString()))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE))
            .andExpect(jsonPath("$.numberOfSatellites").value(DEFAULT_NUMBER_OF_SATELLITES))
            .andExpect(jsonPath("$.sequenceNumber").value(DEFAULT_SEQUENCE_NUMBER))
            .andExpect(jsonPath("$.rssi").value(DEFAULT_RSSI))
            .andExpect(jsonPath("$.ntfEngRequestId").value(DEFAULT_NTF_ENG_REQUEST_ID.toString()))
            .andExpect(jsonPath("$.crashUuid").value(DEFAULT_CRASH_UUID.toString()))
            .andExpect(jsonPath("$.deviceIdType").value(DEFAULT_DEVICE_ID_TYPE.toString()))
            .andExpect(jsonPath("$.crush").value(DEFAULT_CRUSH))
            .andExpect(jsonPath("$.pdof").value(DEFAULT_PDOF))
            .andExpect(jsonPath("$.vin").value(DEFAULT_VIN.toString()))
            .andExpect(jsonPath("$.severityProcessedOn").value(DEFAULT_SEVERITY_PROCESSED_ON.toString()))
            .andExpect(jsonPath("$.severityCode").value(DEFAULT_SEVERITY_CODE.toString()))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.vinRegion").value(DEFAULT_VIN_REGION.toString()))
            .andExpect(jsonPath("$.mpd2").value(DEFAULT_MPD_2))
            .andExpect(jsonPath("$.mpd3").value(DEFAULT_MPD_3))
            .andExpect(jsonPath("$.peakG").value(DEFAULT_PEAK_G))
            .andExpect(jsonPath("$.deltaV").value(DEFAULT_DELTA_V))
            .andExpect(jsonPath("$.vehicleClass").value(DEFAULT_VEHICLE_CLASS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastModifiedOn").value(DEFAULT_LAST_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.street1").value(DEFAULT_STREET_1.toString()))
            .andExpect(jsonPath("$.street2").value(DEFAULT_STREET_2.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.crashDirection").value(DEFAULT_CRASH_DIRECTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComposite() throws Exception {
        // Get the composite
        restCompositeMockMvc.perform(get("/api/composites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComposite() throws Exception {
        // Initialize the database
        compositeRepository.saveAndFlush(composite);
        int databaseSizeBeforeUpdate = compositeRepository.findAll().size();

        // Update the composite
        Composite updatedComposite = compositeRepository.findOne(composite.getId());
        // Disconnect from session so that the updates on updatedComposite are not directly saved in db
        em.detach(updatedComposite);
        updatedComposite
            .deviceId(UPDATED_DEVICE_ID)
            .crashDate(UPDATED_CRASH_DATE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .speed(UPDATED_SPEED)
            .odometer(UPDATED_ODOMETER)
            .heading(UPDATED_HEADING)
            .rawData(UPDATED_RAW_DATA)
            .altitude(UPDATED_ALTITUDE)
            .numberOfSatellites(UPDATED_NUMBER_OF_SATELLITES)
            .sequenceNumber(UPDATED_SEQUENCE_NUMBER)
            .rssi(UPDATED_RSSI)
            .ntfEngRequestId(UPDATED_NTF_ENG_REQUEST_ID)
            .crashUuid(UPDATED_CRASH_UUID)
            .deviceIdType(UPDATED_DEVICE_ID_TYPE)
            .crush(UPDATED_CRUSH)
            .pdof(UPDATED_PDOF)
            .vin(UPDATED_VIN)
            .severityProcessedOn(UPDATED_SEVERITY_PROCESSED_ON)
            .severityCode(UPDATED_SEVERITY_CODE)
            .make(UPDATED_MAKE)
            .model(UPDATED_MODEL)
            .year(UPDATED_YEAR)
            .vinRegion(UPDATED_VIN_REGION)
            .mpd2(UPDATED_MPD_2)
            .mpd3(UPDATED_MPD_3)
            .peakG(UPDATED_PEAK_G)
            .deltaV(UPDATED_DELTA_V)
            .vehicleClass(UPDATED_VEHICLE_CLASS)
            .createdOn(UPDATED_CREATED_ON)
            .lastModifiedOn(UPDATED_LAST_MODIFIED_ON)
            .street1(UPDATED_STREET_1)
            .street2(UPDATED_STREET_2)
            .zip(UPDATED_ZIP)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .crashDirection(UPDATED_CRASH_DIRECTION);

        restCompositeMockMvc.perform(put("/api/composites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComposite)))
            .andExpect(status().isOk());

        // Validate the Composite in the database
        List<Composite> compositeList = compositeRepository.findAll();
        assertThat(compositeList).hasSize(databaseSizeBeforeUpdate);
        Composite testComposite = compositeList.get(compositeList.size() - 1);
        assertThat(testComposite.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testComposite.getCrashDate()).isEqualTo(UPDATED_CRASH_DATE);
        assertThat(testComposite.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testComposite.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testComposite.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testComposite.getOdometer()).isEqualTo(UPDATED_ODOMETER);
        assertThat(testComposite.getHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testComposite.getRawData()).isEqualTo(UPDATED_RAW_DATA);
        assertThat(testComposite.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testComposite.getNumberOfSatellites()).isEqualTo(UPDATED_NUMBER_OF_SATELLITES);
        assertThat(testComposite.getSequenceNumber()).isEqualTo(UPDATED_SEQUENCE_NUMBER);
        assertThat(testComposite.getRssi()).isEqualTo(UPDATED_RSSI);
        assertThat(testComposite.getNtfEngRequestId()).isEqualTo(UPDATED_NTF_ENG_REQUEST_ID);
        assertThat(testComposite.getCrashUuid()).isEqualTo(UPDATED_CRASH_UUID);
        assertThat(testComposite.getDeviceIdType()).isEqualTo(UPDATED_DEVICE_ID_TYPE);
        assertThat(testComposite.getCrush()).isEqualTo(UPDATED_CRUSH);
        assertThat(testComposite.getPdof()).isEqualTo(UPDATED_PDOF);
        assertThat(testComposite.getVin()).isEqualTo(UPDATED_VIN);
        assertThat(testComposite.getSeverityProcessedOn()).isEqualTo(UPDATED_SEVERITY_PROCESSED_ON);
        assertThat(testComposite.getSeverityCode()).isEqualTo(UPDATED_SEVERITY_CODE);
        assertThat(testComposite.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testComposite.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testComposite.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testComposite.getVinRegion()).isEqualTo(UPDATED_VIN_REGION);
        assertThat(testComposite.getMpd2()).isEqualTo(UPDATED_MPD_2);
        assertThat(testComposite.getMpd3()).isEqualTo(UPDATED_MPD_3);
        assertThat(testComposite.getPeakG()).isEqualTo(UPDATED_PEAK_G);
        assertThat(testComposite.getDeltaV()).isEqualTo(UPDATED_DELTA_V);
        assertThat(testComposite.getVehicleClass()).isEqualTo(UPDATED_VEHICLE_CLASS);
        assertThat(testComposite.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testComposite.getLastModifiedOn()).isEqualTo(UPDATED_LAST_MODIFIED_ON);
        assertThat(testComposite.getStreet1()).isEqualTo(UPDATED_STREET_1);
        assertThat(testComposite.getStreet2()).isEqualTo(UPDATED_STREET_2);
        assertThat(testComposite.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testComposite.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testComposite.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testComposite.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testComposite.getCrashDirection()).isEqualTo(UPDATED_CRASH_DIRECTION);
    }

    @Test
    @Transactional
    public void updateNonExistingComposite() throws Exception {
        int databaseSizeBeforeUpdate = compositeRepository.findAll().size();

        // Create the Composite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompositeMockMvc.perform(put("/api/composites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(composite)))
            .andExpect(status().isCreated());

        // Validate the Composite in the database
        List<Composite> compositeList = compositeRepository.findAll();
        assertThat(compositeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComposite() throws Exception {
        // Initialize the database
        compositeRepository.saveAndFlush(composite);
        int databaseSizeBeforeDelete = compositeRepository.findAll().size();

        // Get the composite
        restCompositeMockMvc.perform(delete("/api/composites/{id}", composite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Composite> compositeList = compositeRepository.findAll();
        assertThat(compositeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Composite.class);
        Composite composite1 = new Composite();
        composite1.setId(1L);
        Composite composite2 = new Composite();
        composite2.setId(composite1.getId());
        assertThat(composite1).isEqualTo(composite2);
        composite2.setId(2L);
        assertThat(composite1).isNotEqualTo(composite2);
        composite1.setId(null);
        assertThat(composite1).isNotEqualTo(composite2);
    }
}
