package com.tsystems.conmob.cmam.web.rest;

import com.tsystems.conmob.cmam.IcnpushApp;

import com.tsystems.conmob.cmam.domain.AccelerationPair;
import com.tsystems.conmob.cmam.repository.AccelerationPairRepository;
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
 * Test class for the AccelerationPairResource REST controller.
 *
 * @see AccelerationPairResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcnpushApp.class)
public class AccelerationPairResourceIntTest {

    private static final Integer DEFAULT_ICN_ID = 1;
    private static final Integer UPDATED_ICN_ID = 2;

    private static final Integer DEFAULT_ACC_X = 1;
    private static final Integer UPDATED_ACC_X = 2;

    private static final Integer DEFAULT_ACC_Y = 1;
    private static final Integer UPDATED_ACC_Y = 2;

    private static final Integer DEFAULT_ACC_Z = 1;
    private static final Integer UPDATED_ACC_Z = 2;

    private static final Integer DEFAULT_TIME = 1;
    private static final Integer UPDATED_TIME = 2;

    @Autowired
    private AccelerationPairRepository accelerationPairRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccelerationPairMockMvc;

    private AccelerationPair accelerationPair;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccelerationPairResource accelerationPairResource = new AccelerationPairResource(accelerationPairRepository);
        this.restAccelerationPairMockMvc = MockMvcBuilders.standaloneSetup(accelerationPairResource)
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
    public static AccelerationPair createEntity(EntityManager em) {
        AccelerationPair accelerationPair = new AccelerationPair()
            .icnId(DEFAULT_ICN_ID)
            .accX(DEFAULT_ACC_X)
            .accY(DEFAULT_ACC_Y)
            .accZ(DEFAULT_ACC_Z)
            .time(DEFAULT_TIME);
        return accelerationPair;
    }

    @Before
    public void initTest() {
        accelerationPair = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccelerationPair() throws Exception {
        int databaseSizeBeforeCreate = accelerationPairRepository.findAll().size();

        // Create the AccelerationPair
        restAccelerationPairMockMvc.perform(post("/api/acceleration-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accelerationPair)))
            .andExpect(status().isCreated());

        // Validate the AccelerationPair in the database
        List<AccelerationPair> accelerationPairList = accelerationPairRepository.findAll();
        assertThat(accelerationPairList).hasSize(databaseSizeBeforeCreate + 1);
        AccelerationPair testAccelerationPair = accelerationPairList.get(accelerationPairList.size() - 1);
        assertThat(testAccelerationPair.getIcnId()).isEqualTo(DEFAULT_ICN_ID);
        assertThat(testAccelerationPair.getAccX()).isEqualTo(DEFAULT_ACC_X);
        assertThat(testAccelerationPair.getAccY()).isEqualTo(DEFAULT_ACC_Y);
        assertThat(testAccelerationPair.getAccZ()).isEqualTo(DEFAULT_ACC_Z);
        assertThat(testAccelerationPair.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createAccelerationPairWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accelerationPairRepository.findAll().size();

        // Create the AccelerationPair with an existing ID
        accelerationPair.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccelerationPairMockMvc.perform(post("/api/acceleration-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accelerationPair)))
            .andExpect(status().isBadRequest());

        // Validate the AccelerationPair in the database
        List<AccelerationPair> accelerationPairList = accelerationPairRepository.findAll();
        assertThat(accelerationPairList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccelerationPairs() throws Exception {
        // Initialize the database
        accelerationPairRepository.saveAndFlush(accelerationPair);

        // Get all the accelerationPairList
        restAccelerationPairMockMvc.perform(get("/api/acceleration-pairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accelerationPair.getId().intValue())))
            .andExpect(jsonPath("$.[*].icnId").value(hasItem(DEFAULT_ICN_ID)))
            .andExpect(jsonPath("$.[*].accX").value(hasItem(DEFAULT_ACC_X)))
            .andExpect(jsonPath("$.[*].accY").value(hasItem(DEFAULT_ACC_Y)))
            .andExpect(jsonPath("$.[*].accZ").value(hasItem(DEFAULT_ACC_Z)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getAccelerationPair() throws Exception {
        // Initialize the database
        accelerationPairRepository.saveAndFlush(accelerationPair);

        // Get the accelerationPair
        restAccelerationPairMockMvc.perform(get("/api/acceleration-pairs/{id}", accelerationPair.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accelerationPair.getId().intValue()))
            .andExpect(jsonPath("$.icnId").value(DEFAULT_ICN_ID))
            .andExpect(jsonPath("$.accX").value(DEFAULT_ACC_X))
            .andExpect(jsonPath("$.accY").value(DEFAULT_ACC_Y))
            .andExpect(jsonPath("$.accZ").value(DEFAULT_ACC_Z))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingAccelerationPair() throws Exception {
        // Get the accelerationPair
        restAccelerationPairMockMvc.perform(get("/api/acceleration-pairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccelerationPair() throws Exception {
        // Initialize the database
        accelerationPairRepository.saveAndFlush(accelerationPair);
        int databaseSizeBeforeUpdate = accelerationPairRepository.findAll().size();

        // Update the accelerationPair
        AccelerationPair updatedAccelerationPair = accelerationPairRepository.findOne(accelerationPair.getId());
        // Disconnect from session so that the updates on updatedAccelerationPair are not directly saved in db
        em.detach(updatedAccelerationPair);
        updatedAccelerationPair
            .icnId(UPDATED_ICN_ID)
            .accX(UPDATED_ACC_X)
            .accY(UPDATED_ACC_Y)
            .accZ(UPDATED_ACC_Z)
            .time(UPDATED_TIME);

        restAccelerationPairMockMvc.perform(put("/api/acceleration-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccelerationPair)))
            .andExpect(status().isOk());

        // Validate the AccelerationPair in the database
        List<AccelerationPair> accelerationPairList = accelerationPairRepository.findAll();
        assertThat(accelerationPairList).hasSize(databaseSizeBeforeUpdate);
        AccelerationPair testAccelerationPair = accelerationPairList.get(accelerationPairList.size() - 1);
        assertThat(testAccelerationPair.getIcnId()).isEqualTo(UPDATED_ICN_ID);
        assertThat(testAccelerationPair.getAccX()).isEqualTo(UPDATED_ACC_X);
        assertThat(testAccelerationPair.getAccY()).isEqualTo(UPDATED_ACC_Y);
        assertThat(testAccelerationPair.getAccZ()).isEqualTo(UPDATED_ACC_Z);
        assertThat(testAccelerationPair.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAccelerationPair() throws Exception {
        int databaseSizeBeforeUpdate = accelerationPairRepository.findAll().size();

        // Create the AccelerationPair

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccelerationPairMockMvc.perform(put("/api/acceleration-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accelerationPair)))
            .andExpect(status().isCreated());

        // Validate the AccelerationPair in the database
        List<AccelerationPair> accelerationPairList = accelerationPairRepository.findAll();
        assertThat(accelerationPairList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAccelerationPair() throws Exception {
        // Initialize the database
        accelerationPairRepository.saveAndFlush(accelerationPair);
        int databaseSizeBeforeDelete = accelerationPairRepository.findAll().size();

        // Get the accelerationPair
        restAccelerationPairMockMvc.perform(delete("/api/acceleration-pairs/{id}", accelerationPair.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccelerationPair> accelerationPairList = accelerationPairRepository.findAll();
        assertThat(accelerationPairList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccelerationPair.class);
        AccelerationPair accelerationPair1 = new AccelerationPair();
        accelerationPair1.setId(1L);
        AccelerationPair accelerationPair2 = new AccelerationPair();
        accelerationPair2.setId(accelerationPair1.getId());
        assertThat(accelerationPair1).isEqualTo(accelerationPair2);
        accelerationPair2.setId(2L);
        assertThat(accelerationPair1).isNotEqualTo(accelerationPair2);
        accelerationPair1.setId(null);
        assertThat(accelerationPair1).isNotEqualTo(accelerationPair2);
    }
}
