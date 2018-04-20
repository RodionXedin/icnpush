package com.tsystems.conmob.cmam.web.rest;

import com.tsystems.conmob.cmam.IcnpushApp;

import com.tsystems.conmob.cmam.domain.ICNPush;
import com.tsystems.conmob.cmam.repository.ICNPushRepository;
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
 * Test class for the ICNPushResource REST controller.
 *
 * @see ICNPushResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IcnpushApp.class)
public class ICNPushResourceIntTest {

    private static final String DEFAULT_ESN = "AAAAAAAAAA";
    private static final String UPDATED_ESN = "BBBBBBBBBB";

    private static final String DEFAULT_DATETIME = "AAAAAAAAAA";
    private static final String UPDATED_DATETIME = "BBBBBBBBBB";

    @Autowired
    private ICNPushRepository iCNPushRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restICNPushMockMvc;

    private ICNPush iCNPush;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ICNPushResource iCNPushResource = new ICNPushResource(iCNPushRepository);
        this.restICNPushMockMvc = MockMvcBuilders.standaloneSetup(iCNPushResource)
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
    public static ICNPush createEntity(EntityManager em) {
        ICNPush iCNPush = new ICNPush()
            .esn(DEFAULT_ESN)
            .datetime(DEFAULT_DATETIME);
        return iCNPush;
    }

    @Before
    public void initTest() {
        iCNPush = createEntity(em);
    }

    @Test
    @Transactional
    public void createICNPush() throws Exception {
        int databaseSizeBeforeCreate = iCNPushRepository.findAll().size();

        // Create the ICNPush
        restICNPushMockMvc.perform(post("/api/icn-pushes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iCNPush)))
            .andExpect(status().isCreated());

        // Validate the ICNPush in the database
        List<ICNPush> iCNPushList = iCNPushRepository.findAll();
        assertThat(iCNPushList).hasSize(databaseSizeBeforeCreate + 1);
        ICNPush testICNPush = iCNPushList.get(iCNPushList.size() - 1);
        assertThat(testICNPush.getEsn()).isEqualTo(DEFAULT_ESN);
        assertThat(testICNPush.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    public void createICNPushWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iCNPushRepository.findAll().size();

        // Create the ICNPush with an existing ID
        iCNPush.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restICNPushMockMvc.perform(post("/api/icn-pushes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iCNPush)))
            .andExpect(status().isBadRequest());

        // Validate the ICNPush in the database
        List<ICNPush> iCNPushList = iCNPushRepository.findAll();
        assertThat(iCNPushList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllICNPushes() throws Exception {
        // Initialize the database
        iCNPushRepository.saveAndFlush(iCNPush);

        // Get all the iCNPushList
        restICNPushMockMvc.perform(get("/api/icn-pushes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iCNPush.getId().intValue())))
            .andExpect(jsonPath("$.[*].esn").value(hasItem(DEFAULT_ESN.toString())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(DEFAULT_DATETIME.toString())));
    }

    @Test
    @Transactional
    public void getICNPush() throws Exception {
        // Initialize the database
        iCNPushRepository.saveAndFlush(iCNPush);

        // Get the iCNPush
        restICNPushMockMvc.perform(get("/api/icn-pushes/{id}", iCNPush.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iCNPush.getId().intValue()))
            .andExpect(jsonPath("$.esn").value(DEFAULT_ESN.toString()))
            .andExpect(jsonPath("$.datetime").value(DEFAULT_DATETIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingICNPush() throws Exception {
        // Get the iCNPush
        restICNPushMockMvc.perform(get("/api/icn-pushes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateICNPush() throws Exception {
        // Initialize the database
        iCNPushRepository.saveAndFlush(iCNPush);
        int databaseSizeBeforeUpdate = iCNPushRepository.findAll().size();

        // Update the iCNPush
        ICNPush updatedICNPush = iCNPushRepository.findOne(iCNPush.getId());
        // Disconnect from session so that the updates on updatedICNPush are not directly saved in db
        em.detach(updatedICNPush);
        updatedICNPush
            .esn(UPDATED_ESN)
            .datetime(UPDATED_DATETIME);

        restICNPushMockMvc.perform(put("/api/icn-pushes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedICNPush)))
            .andExpect(status().isOk());

        // Validate the ICNPush in the database
        List<ICNPush> iCNPushList = iCNPushRepository.findAll();
        assertThat(iCNPushList).hasSize(databaseSizeBeforeUpdate);
        ICNPush testICNPush = iCNPushList.get(iCNPushList.size() - 1);
        assertThat(testICNPush.getEsn()).isEqualTo(UPDATED_ESN);
        assertThat(testICNPush.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingICNPush() throws Exception {
        int databaseSizeBeforeUpdate = iCNPushRepository.findAll().size();

        // Create the ICNPush

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restICNPushMockMvc.perform(put("/api/icn-pushes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iCNPush)))
            .andExpect(status().isCreated());

        // Validate the ICNPush in the database
        List<ICNPush> iCNPushList = iCNPushRepository.findAll();
        assertThat(iCNPushList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteICNPush() throws Exception {
        // Initialize the database
        iCNPushRepository.saveAndFlush(iCNPush);
        int databaseSizeBeforeDelete = iCNPushRepository.findAll().size();

        // Get the iCNPush
        restICNPushMockMvc.perform(delete("/api/icn-pushes/{id}", iCNPush.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ICNPush> iCNPushList = iCNPushRepository.findAll();
        assertThat(iCNPushList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ICNPush.class);
        ICNPush iCNPush1 = new ICNPush();
        iCNPush1.setId(1L);
        ICNPush iCNPush2 = new ICNPush();
        iCNPush2.setId(iCNPush1.getId());
        assertThat(iCNPush1).isEqualTo(iCNPush2);
        iCNPush2.setId(2L);
        assertThat(iCNPush1).isNotEqualTo(iCNPush2);
        iCNPush1.setId(null);
        assertThat(iCNPush1).isNotEqualTo(iCNPush2);
    }
}
