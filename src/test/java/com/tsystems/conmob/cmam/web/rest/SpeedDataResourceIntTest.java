package com.tsystems.conmob.cmam.web.rest;

import com.tsystems.conmob.cmam.IcnpushApp;

import com.tsystems.conmob.cmam.domain.SpeedData;
import com.tsystems.conmob.cmam.repository.SpeedDataRepository;
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
 * Test class for the SpeedDataResource REST controller.
 *
 * @see SpeedDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcnpushApp.class)
public class SpeedDataResourceIntTest {

    private static final Integer DEFAULT_ICN_ID = 1;
    private static final Integer UPDATED_ICN_ID = 2;

    private static final Integer DEFAULT_LATITUDE = 1;
    private static final Integer UPDATED_LATITUDE = 2;

    private static final Integer DEFAULT_LONGITUDE = 1;
    private static final Integer UPDATED_LONGITUDE = 2;

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer UPDATED_SPEED = 2;

    private static final Integer DEFAULT_ALTITUDE = 1;
    private static final Integer UPDATED_ALTITUDE = 2;

    private static final Integer DEFAULT_TIME = 1;
    private static final Integer UPDATED_TIME = 2;

    @Autowired
    private SpeedDataRepository speedDataRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpeedDataMockMvc;

    private SpeedData speedData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpeedDataResource speedDataResource = new SpeedDataResource(speedDataRepository);
        this.restSpeedDataMockMvc = MockMvcBuilders.standaloneSetup(speedDataResource)
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
    public static SpeedData createEntity(EntityManager em) {
        SpeedData speedData = new SpeedData()
            .icnId(DEFAULT_ICN_ID)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .speed(DEFAULT_SPEED)
            .altitude(DEFAULT_ALTITUDE)
            .time(DEFAULT_TIME);
        return speedData;
    }

    @Before
    public void initTest() {
        speedData = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeedData() throws Exception {
        int databaseSizeBeforeCreate = speedDataRepository.findAll().size();

        // Create the SpeedData
        restSpeedDataMockMvc.perform(post("/api/speed-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speedData)))
            .andExpect(status().isCreated());

        // Validate the SpeedData in the database
        List<SpeedData> speedDataList = speedDataRepository.findAll();
        assertThat(speedDataList).hasSize(databaseSizeBeforeCreate + 1);
        SpeedData testSpeedData = speedDataList.get(speedDataList.size() - 1);
        assertThat(testSpeedData.getIcnId()).isEqualTo(DEFAULT_ICN_ID);
        assertThat(testSpeedData.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSpeedData.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSpeedData.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testSpeedData.getAltitude()).isEqualTo(DEFAULT_ALTITUDE);
        assertThat(testSpeedData.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createSpeedDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speedDataRepository.findAll().size();

        // Create the SpeedData with an existing ID
        speedData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeedDataMockMvc.perform(post("/api/speed-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speedData)))
            .andExpect(status().isBadRequest());

        // Validate the SpeedData in the database
        List<SpeedData> speedDataList = speedDataRepository.findAll();
        assertThat(speedDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSpeedData() throws Exception {
        // Initialize the database
        speedDataRepository.saveAndFlush(speedData);

        // Get all the speedDataList
        restSpeedDataMockMvc.perform(get("/api/speed-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speedData.getId().intValue())))
            .andExpect(jsonPath("$.[*].icnId").value(hasItem(DEFAULT_ICN_ID)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED)))
            .andExpect(jsonPath("$.[*].altitude").value(hasItem(DEFAULT_ALTITUDE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getSpeedData() throws Exception {
        // Initialize the database
        speedDataRepository.saveAndFlush(speedData);

        // Get the speedData
        restSpeedDataMockMvc.perform(get("/api/speed-data/{id}", speedData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speedData.getId().intValue()))
            .andExpect(jsonPath("$.icnId").value(DEFAULT_ICN_ID))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED))
            .andExpect(jsonPath("$.altitude").value(DEFAULT_ALTITUDE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingSpeedData() throws Exception {
        // Get the speedData
        restSpeedDataMockMvc.perform(get("/api/speed-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeedData() throws Exception {
        // Initialize the database
        speedDataRepository.saveAndFlush(speedData);
        int databaseSizeBeforeUpdate = speedDataRepository.findAll().size();

        // Update the speedData
        SpeedData updatedSpeedData = speedDataRepository.findOne(speedData.getId());
        // Disconnect from session so that the updates on updatedSpeedData are not directly saved in db
        em.detach(updatedSpeedData);
        updatedSpeedData
            .icnId(UPDATED_ICN_ID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .speed(UPDATED_SPEED)
            .altitude(UPDATED_ALTITUDE)
            .time(UPDATED_TIME);

        restSpeedDataMockMvc.perform(put("/api/speed-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeedData)))
            .andExpect(status().isOk());

        // Validate the SpeedData in the database
        List<SpeedData> speedDataList = speedDataRepository.findAll();
        assertThat(speedDataList).hasSize(databaseSizeBeforeUpdate);
        SpeedData testSpeedData = speedDataList.get(speedDataList.size() - 1);
        assertThat(testSpeedData.getIcnId()).isEqualTo(UPDATED_ICN_ID);
        assertThat(testSpeedData.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSpeedData.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSpeedData.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testSpeedData.getAltitude()).isEqualTo(UPDATED_ALTITUDE);
        assertThat(testSpeedData.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeedData() throws Exception {
        int databaseSizeBeforeUpdate = speedDataRepository.findAll().size();

        // Create the SpeedData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpeedDataMockMvc.perform(put("/api/speed-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speedData)))
            .andExpect(status().isCreated());

        // Validate the SpeedData in the database
        List<SpeedData> speedDataList = speedDataRepository.findAll();
        assertThat(speedDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpeedData() throws Exception {
        // Initialize the database
        speedDataRepository.saveAndFlush(speedData);
        int databaseSizeBeforeDelete = speedDataRepository.findAll().size();

        // Get the speedData
        restSpeedDataMockMvc.perform(delete("/api/speed-data/{id}", speedData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpeedData> speedDataList = speedDataRepository.findAll();
        assertThat(speedDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeedData.class);
        SpeedData speedData1 = new SpeedData();
        speedData1.setId(1L);
        SpeedData speedData2 = new SpeedData();
        speedData2.setId(speedData1.getId());
        assertThat(speedData1).isEqualTo(speedData2);
        speedData2.setId(2L);
        assertThat(speedData1).isNotEqualTo(speedData2);
        speedData1.setId(null);
        assertThat(speedData1).isNotEqualTo(speedData2);
    }
}
