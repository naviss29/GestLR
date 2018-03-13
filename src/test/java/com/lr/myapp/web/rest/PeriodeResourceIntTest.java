package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.Periode;
import com.lr.myapp.repository.PeriodeRepository;
import com.lr.myapp.service.PeriodeService;
import com.lr.myapp.service.dto.PeriodeDTO;
import com.lr.myapp.service.mapper.PeriodeMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lr.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeriodeResource REST controller.
 *
 * @see PeriodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class PeriodeResourceIntTest {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final Instant DEFAULT_ECHEANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ECHEANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private PeriodeMapper periodeMapper;

    @Autowired
    private PeriodeService periodeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodeMockMvc;

    private Periode periode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodeResource periodeResource = new PeriodeResource(periodeService);
        this.restPeriodeMockMvc = MockMvcBuilders.standaloneSetup(periodeResource)
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
    public static Periode createEntity(EntityManager em) {
        Periode periode = new Periode()
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .echeance(DEFAULT_ECHEANCE)
            .statut(DEFAULT_STATUT);
        return periode;
    }

    @Before
    public void initTest() {
        periode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriode() throws Exception {
        int databaseSizeBeforeCreate = periodeRepository.findAll().size();

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);
        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate + 1);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPeriode.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testPeriode.getEcheance()).isEqualTo(DEFAULT_ECHEANCE);
        assertThat(testPeriode.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createPeriodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodeRepository.findAll().size();

        // Create the Periode with an existing ID
        periode.setId(1L);
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setAnnee(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setMois(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setStatut(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodes() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get all the periodeList
        restPeriodeMockMvc.perform(get("/api/periodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periode.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].echeance").value(hasItem(DEFAULT_ECHEANCE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }

    @Test
    @Transactional
    public void getPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get the periode
        restPeriodeMockMvc.perform(get("/api/periodes/{id}", periode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periode.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.echeance").value(DEFAULT_ECHEANCE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriode() throws Exception {
        // Get the periode
        restPeriodeMockMvc.perform(get("/api/periodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Update the periode
        Periode updatedPeriode = periodeRepository.findOne(periode.getId());
        // Disconnect from session so that the updates on updatedPeriode are not directly saved in db
        em.detach(updatedPeriode);
        updatedPeriode
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .echeance(UPDATED_ECHEANCE)
            .statut(UPDATED_STATUT);
        PeriodeDTO periodeDTO = periodeMapper.toDto(updatedPeriode);

        restPeriodeMockMvc.perform(put("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isOk());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPeriode.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testPeriode.getEcheance()).isEqualTo(UPDATED_ECHEANCE);
        assertThat(testPeriode.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodeMockMvc.perform(put("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);
        int databaseSizeBeforeDelete = periodeRepository.findAll().size();

        // Get the periode
        restPeriodeMockMvc.perform(delete("/api/periodes/{id}", periode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periode.class);
        Periode periode1 = new Periode();
        periode1.setId(1L);
        Periode periode2 = new Periode();
        periode2.setId(periode1.getId());
        assertThat(periode1).isEqualTo(periode2);
        periode2.setId(2L);
        assertThat(periode1).isNotEqualTo(periode2);
        periode1.setId(null);
        assertThat(periode1).isNotEqualTo(periode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodeDTO.class);
        PeriodeDTO periodeDTO1 = new PeriodeDTO();
        periodeDTO1.setId(1L);
        PeriodeDTO periodeDTO2 = new PeriodeDTO();
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
        periodeDTO2.setId(periodeDTO1.getId());
        assertThat(periodeDTO1).isEqualTo(periodeDTO2);
        periodeDTO2.setId(2L);
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
        periodeDTO1.setId(null);
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodeMapper.fromId(null)).isNull();
    }
}
