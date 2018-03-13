package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.Commentaire;
import com.lr.myapp.repository.CommentaireRepository;
import com.lr.myapp.service.CommentaireService;
import com.lr.myapp.service.dto.CommentaireDTO;
import com.lr.myapp.service.mapper.CommentaireMapper;
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
 * Test class for the CommentaireResource REST controller.
 *
 * @see CommentaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class CommentaireResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_SAISIE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SAISIE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private CommentaireMapper commentaireMapper;

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentaireMockMvc;

    private Commentaire commentaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentaireResource commentaireResource = new CommentaireResource(commentaireService);
        this.restCommentaireMockMvc = MockMvcBuilders.standaloneSetup(commentaireResource)
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
    public static Commentaire createEntity(EntityManager em) {
        Commentaire commentaire = new Commentaire()
            .libelle(DEFAULT_LIBELLE)
            .auteur(DEFAULT_AUTEUR)
            .dateSaisie(DEFAULT_DATE_SAISIE);
        return commentaire;
    }

    @Before
    public void initTest() {
        commentaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentaire() throws Exception {
        int databaseSizeBeforeCreate = commentaireRepository.findAll().size();

        // Create the Commentaire
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);
        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeCreate + 1);
        Commentaire testCommentaire = commentaireList.get(commentaireList.size() - 1);
        assertThat(testCommentaire.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCommentaire.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testCommentaire.getDateSaisie()).isEqualTo(DEFAULT_DATE_SAISIE);
    }

    @Test
    @Transactional
    public void createCommentaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentaireRepository.findAll().size();

        // Create the Commentaire with an existing ID
        commentaire.setId(1L);
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaireRepository.findAll().size();
        // set the field null
        commentaire.setLibelle(null);

        // Create the Commentaire, which fails.
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);

        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isBadRequest());

        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaireRepository.findAll().size();
        // set the field null
        commentaire.setAuteur(null);

        // Create the Commentaire, which fails.
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);

        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isBadRequest());

        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateSaisieIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaireRepository.findAll().size();
        // set the field null
        commentaire.setDateSaisie(null);

        // Create the Commentaire, which fails.
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);

        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isBadRequest());

        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommentaires() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        // Get all the commentaireList
        restCommentaireMockMvc.perform(get("/api/commentaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR.toString())))
            .andExpect(jsonPath("$.[*].dateSaisie").value(hasItem(DEFAULT_DATE_SAISIE.toString())));
    }

    @Test
    @Transactional
    public void getCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        // Get the commentaire
        restCommentaireMockMvc.perform(get("/api/commentaires/{id}", commentaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentaire.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR.toString()))
            .andExpect(jsonPath("$.dateSaisie").value(DEFAULT_DATE_SAISIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentaire() throws Exception {
        // Get the commentaire
        restCommentaireMockMvc.perform(get("/api/commentaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);
        int databaseSizeBeforeUpdate = commentaireRepository.findAll().size();

        // Update the commentaire
        Commentaire updatedCommentaire = commentaireRepository.findOne(commentaire.getId());
        // Disconnect from session so that the updates on updatedCommentaire are not directly saved in db
        em.detach(updatedCommentaire);
        updatedCommentaire
            .libelle(UPDATED_LIBELLE)
            .auteur(UPDATED_AUTEUR)
            .dateSaisie(UPDATED_DATE_SAISIE);
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(updatedCommentaire);

        restCommentaireMockMvc.perform(put("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isOk());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeUpdate);
        Commentaire testCommentaire = commentaireList.get(commentaireList.size() - 1);
        assertThat(testCommentaire.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCommentaire.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testCommentaire.getDateSaisie()).isEqualTo(UPDATED_DATE_SAISIE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentaire() throws Exception {
        int databaseSizeBeforeUpdate = commentaireRepository.findAll().size();

        // Create the Commentaire
        CommentaireDTO commentaireDTO = commentaireMapper.toDto(commentaire);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentaireMockMvc.perform(put("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);
        int databaseSizeBeforeDelete = commentaireRepository.findAll().size();

        // Get the commentaire
        restCommentaireMockMvc.perform(delete("/api/commentaires/{id}", commentaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commentaire.class);
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(commentaire1.getId());
        assertThat(commentaire1).isEqualTo(commentaire2);
        commentaire2.setId(2L);
        assertThat(commentaire1).isNotEqualTo(commentaire2);
        commentaire1.setId(null);
        assertThat(commentaire1).isNotEqualTo(commentaire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentaireDTO.class);
        CommentaireDTO commentaireDTO1 = new CommentaireDTO();
        commentaireDTO1.setId(1L);
        CommentaireDTO commentaireDTO2 = new CommentaireDTO();
        assertThat(commentaireDTO1).isNotEqualTo(commentaireDTO2);
        commentaireDTO2.setId(commentaireDTO1.getId());
        assertThat(commentaireDTO1).isEqualTo(commentaireDTO2);
        commentaireDTO2.setId(2L);
        assertThat(commentaireDTO1).isNotEqualTo(commentaireDTO2);
        commentaireDTO1.setId(null);
        assertThat(commentaireDTO1).isNotEqualTo(commentaireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commentaireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commentaireMapper.fromId(null)).isNull();
    }
}
