package com.tsystems.conmob.cmam.web.rest;

import com.tsystems.conmob.cmam.IcnpushApp;

import com.tsystems.conmob.cmam.domain.Crash;
import com.tsystems.conmob.cmam.repository.CrashRepository;
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
 * Test class for the CrashResource REST controller.
 *
 * @see CrashResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcnpushApp.class)
public class CrashResourceIntTest {

    private static final Integer DEFAULT_SEVERITY = 1;
    private static final Integer UPDATED_SEVERITY = 2;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private CrashRepository crashRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCrashMockMvc;

    private Crash crash;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CrashResource crashResource = new CrashResource(crashRepository);
        this.restCrashMockMvc = MockMvcBuilders.standaloneSetup(crashResource)
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
    public static Crash createEntity(EntityManager em) {
        Crash crash = new Crash()
            .severity(DEFAULT_SEVERITY)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .address(DEFAULT_ADDRESS);
        return crash;
    }

    @Before
    public void initTest() {
        crash = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrash() throws Exception {
        int databaseSizeBeforeCreate = crashRepository.findAll().size();

        // Create the Crash
        restCrashMockMvc.perform(post("/api/crashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crash)))
            .andExpect(status().isCreated());

        // Validate the Crash in the database
        List<Crash> crashList = crashRepository.findAll();
        assertThat(crashList).hasSize(databaseSizeBeforeCreate + 1);
        Crash testCrash = crashList.get(crashList.size() - 1);
        assertThat(testCrash.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testCrash.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCrash.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCrash.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createCrashWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crashRepository.findAll().size();

        // Create the Crash with an existing ID
        crash.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrashMockMvc.perform(post("/api/crashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crash)))
            .andExpect(status().isBadRequest());

        // Validate the Crash in the database
        List<Crash> crashList = crashRepository.findAll();
        assertThat(crashList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrashes() throws Exception {
        // Initialize the database
        crashRepository.saveAndFlush(crash);

        // Get all the crashList
        restCrashMockMvc.perform(get("/api/crashes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crash.getId().intValue())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getCrash() throws Exception {
        // Initialize the database
        crashRepository.saveAndFlush(crash);

        // Get the crash
        restCrashMockMvc.perform(get("/api/crashes/{id}", crash.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crash.getId().intValue()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrash() throws Exception {
        // Get the crash
        restCrashMockMvc.perform(get("/api/crashes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrash() throws Exception {
        // Initialize the database
        crashRepository.saveAndFlush(crash);
        int databaseSizeBeforeUpdate = crashRepository.findAll().size();

        // Update the crash
        Crash updatedCrash = crashRepository.findOne(crash.getId());
        // Disconnect from session so that the updates on updatedCrash are not directly saved in db
        em.detach(updatedCrash);
        updatedCrash
            .severity(UPDATED_SEVERITY)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS);

        restCrashMockMvc.perform(put("/api/crashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrash)))
            .andExpect(status().isOk());

        // Validate the Crash in the database
        List<Crash> crashList = crashRepository.findAll();
        assertThat(crashList).hasSize(databaseSizeBeforeUpdate);
        Crash testCrash = crashList.get(crashList.size() - 1);
        assertThat(testCrash.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testCrash.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCrash.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCrash.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCrash() throws Exception {
        int databaseSizeBeforeUpdate = crashRepository.findAll().size();

        // Create the Crash

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrashMockMvc.perform(put("/api/crashes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crash)))
            .andExpect(status().isCreated());

        // Validate the Crash in the database
        List<Crash> crashList = crashRepository.findAll();
        assertThat(crashList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrash() throws Exception {
        // Initialize the database
        crashRepository.saveAndFlush(crash);
        int databaseSizeBeforeDelete = crashRepository.findAll().size();

        // Get the crash
        restCrashMockMvc.perform(delete("/api/crashes/{id}", crash.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Crash> crashList = crashRepository.findAll();
        assertThat(crashList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crash.class);
        Crash crash1 = new Crash();
        crash1.setId(1L);
        Crash crash2 = new Crash();
        crash2.setId(crash1.getId());
        assertThat(crash1).isEqualTo(crash2);
        crash2.setId(2L);
        assertThat(crash1).isNotEqualTo(crash2);
        crash1.setId(null);
        assertThat(crash1).isNotEqualTo(crash2);
    }
}
