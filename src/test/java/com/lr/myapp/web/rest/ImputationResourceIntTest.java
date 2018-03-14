package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.Imputation;
import com.lr.myapp.repository.ImputationRepository;
import com.lr.myapp.service.ImputationService;
import com.lr.myapp.service.dto.ImputationDTO;
import com.lr.myapp.service.mapper.ImputationMapper;
import com.lr.myapp.web.rest.errors.ExceptionTranslator;

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

import static com.lr.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImputationResource REST controller.
 *
 * @see ImputationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class ImputationResourceIntTest {

    private static final Integer DEFAULT_JOUR = 1;
    private static final Integer UPDATED_JOUR = 2;

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final Float DEFAULT_DUREE = 1F;
    private static final Float UPDATED_DUREE = 2F;

    @Autowired
    private ImputationRepository imputationRepository;

    @Autowired
    private ImputationMapper imputationMapper;

    @Autowired
    private ImputationService imputationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImputationMockMvc;

    private Imputation imputation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImputationResource imputationResource = new ImputationResource(imputationService);
        this.restImputationMockMvc = MockMvcBuilders.standaloneSetup(imputationResource)
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
    public static Imputation createEntity(EntityManager em) {
        Imputation imputation = new Imputation()
            .jour(DEFAULT_JOUR)
            .client(DEFAULT_CLIENT)
            .duree(DEFAULT_DUREE);
        return imputation;
    }

    @Before
    public void initTest() {
        imputation = createEntity(em);
    }

    @Test
    @Transactional
    public void createImputation() throws Exception {
        int databaseSizeBeforeCreate = imputationRepository.findAll().size();

        // Create the Imputation
        ImputationDTO imputationDTO = imputationMapper.toDto(imputation);
        restImputationMockMvc.perform(post("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isCreated());

        // Validate the Imputation in the database
        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeCreate + 1);
        Imputation testImputation = imputationList.get(imputationList.size() - 1);
        assertThat(testImputation.getJour()).isEqualTo(DEFAULT_JOUR);
        assertThat(testImputation.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testImputation.getDuree()).isEqualTo(DEFAULT_DUREE);
    }

    @Test
    @Transactional
    public void createImputationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imputationRepository.findAll().size();

        // Create the Imputation with an existing ID
        imputation.setId(1L);
        ImputationDTO imputationDTO = imputationMapper.toDto(imputation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImputationMockMvc.perform(post("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imputation in the database
        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = imputationRepository.findAll().size();
        // set the field null
        imputation.setJour(null);

        // Create the Imputation, which fails.
        ImputationDTO imputationDTO = imputationMapper.toDto(imputation);

        restImputationMockMvc.perform(post("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isBadRequest());

        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = imputationRepository.findAll().size();
        // set the field null
        imputation.setClient(null);

        // Create the Imputation, which fails.
        ImputationDTO imputationDTO = imputationMapper.toDto(imputation);

        restImputationMockMvc.perform(post("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isBadRequest());

        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImputations() throws Exception {
        // Initialize the database
        imputationRepository.saveAndFlush(imputation);

        // Get all the imputationList
        restImputationMockMvc.perform(get("/api/imputations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imputation.getId().intValue())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR)))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.doubleValue())));
    }

    @Test
    @Transactional
    public void getImputation() throws Exception {
        // Initialize the database
        imputationRepository.saveAndFlush(imputation);

        // Get the imputation
        restImputationMockMvc.perform(get("/api/imputations/{id}", imputation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imputation.getId().intValue()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImputation() throws Exception {
        // Get the imputation
        restImputationMockMvc.perform(get("/api/imputations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImputation() throws Exception {
        // Initialize the database
        imputationRepository.saveAndFlush(imputation);
        int databaseSizeBeforeUpdate = imputationRepository.findAll().size();

        // Update the imputation
        Imputation updatedImputation = imputationRepository.findOne(imputation.getId());
        // Disconnect from session so that the updates on updatedImputation are not directly saved in db
        em.detach(updatedImputation);
        updatedImputation
            .jour(UPDATED_JOUR)
            .client(UPDATED_CLIENT)
            .duree(UPDATED_DUREE);
        ImputationDTO imputationDTO = imputationMapper.toDto(updatedImputation);

        restImputationMockMvc.perform(put("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isOk());

        // Validate the Imputation in the database
        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeUpdate);
        Imputation testImputation = imputationList.get(imputationList.size() - 1);
        assertThat(testImputation.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testImputation.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testImputation.getDuree()).isEqualTo(UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void updateNonExistingImputation() throws Exception {
        int databaseSizeBeforeUpdate = imputationRepository.findAll().size();

        // Create the Imputation
        ImputationDTO imputationDTO = imputationMapper.toDto(imputation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImputationMockMvc.perform(put("/api/imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imputationDTO)))
            .andExpect(status().isCreated());

        // Validate the Imputation in the database
        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImputation() throws Exception {
        // Initialize the database
        imputationRepository.saveAndFlush(imputation);
        int databaseSizeBeforeDelete = imputationRepository.findAll().size();

        // Get the imputation
        restImputationMockMvc.perform(delete("/api/imputations/{id}", imputation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imputation> imputationList = imputationRepository.findAll();
        assertThat(imputationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imputation.class);
        Imputation imputation1 = new Imputation();
        imputation1.setId(1L);
        Imputation imputation2 = new Imputation();
        imputation2.setId(imputation1.getId());
        assertThat(imputation1).isEqualTo(imputation2);
        imputation2.setId(2L);
        assertThat(imputation1).isNotEqualTo(imputation2);
        imputation1.setId(null);
        assertThat(imputation1).isNotEqualTo(imputation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImputationDTO.class);
        ImputationDTO imputationDTO1 = new ImputationDTO();
        imputationDTO1.setId(1L);
        ImputationDTO imputationDTO2 = new ImputationDTO();
        assertThat(imputationDTO1).isNotEqualTo(imputationDTO2);
        imputationDTO2.setId(imputationDTO1.getId());
        assertThat(imputationDTO1).isEqualTo(imputationDTO2);
        imputationDTO2.setId(2L);
        assertThat(imputationDTO1).isNotEqualTo(imputationDTO2);
        imputationDTO1.setId(null);
        assertThat(imputationDTO1).isNotEqualTo(imputationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imputationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imputationMapper.fromId(null)).isNull();
    }
}
