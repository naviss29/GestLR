package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.Signature;
import com.lr.myapp.repository.SignatureRepository;
import com.lr.myapp.service.SignatureService;
import com.lr.myapp.service.dto.SignatureDTO;
import com.lr.myapp.service.mapper.SignatureMapper;
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
 * Test class for the SignatureResource REST controller.
 *
 * @see SignatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class SignatureResourceIntTest {

    private static final String DEFAULT_NOM_SIGNATAIRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SIGNATAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_SIGNATAIRE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_SIGNATAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SIGNATAIRE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SIGNATAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private SignatureMapper signatureMapper;

    @Autowired
    private SignatureService signatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSignatureMockMvc;

    private Signature signature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SignatureResource signatureResource = new SignatureResource(signatureService);
        this.restSignatureMockMvc = MockMvcBuilders.standaloneSetup(signatureResource)
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
    public static Signature createEntity(EntityManager em) {
        Signature signature = new Signature()
            .nomSignataire(DEFAULT_NOM_SIGNATAIRE)
            .prenomSignataire(DEFAULT_PRENOM_SIGNATAIRE)
            .emailSignataire(DEFAULT_EMAIL_SIGNATAIRE)
            .statut(DEFAULT_STATUT);
        return signature;
    }

    @Before
    public void initTest() {
        signature = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignature() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate + 1);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getNomSignataire()).isEqualTo(DEFAULT_NOM_SIGNATAIRE);
        assertThat(testSignature.getPrenomSignataire()).isEqualTo(DEFAULT_PRENOM_SIGNATAIRE);
        assertThat(testSignature.getEmailSignataire()).isEqualTo(DEFAULT_EMAIL_SIGNATAIRE);
        assertThat(testSignature.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createSignatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signatureRepository.findAll().size();

        // Create the Signature with an existing ID
        signature.setId(1L);
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomSignataireIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRepository.findAll().size();
        // set the field null
        signature.setNomSignataire(null);

        // Create the Signature, which fails.
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isBadRequest());

        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomSignataireIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRepository.findAll().size();
        // set the field null
        signature.setPrenomSignataire(null);

        // Create the Signature, which fails.
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isBadRequest());

        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailSignataireIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRepository.findAll().size();
        // set the field null
        signature.setEmailSignataire(null);

        // Create the Signature, which fails.
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isBadRequest());

        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = signatureRepository.findAll().size();
        // set the field null
        signature.setStatut(null);

        // Create the Signature, which fails.
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        restSignatureMockMvc.perform(post("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isBadRequest());

        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSignatures() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get all the signatureList
        restSignatureMockMvc.perform(get("/api/signatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signature.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSignataire").value(hasItem(DEFAULT_NOM_SIGNATAIRE.toString())))
            .andExpect(jsonPath("$.[*].prenomSignataire").value(hasItem(DEFAULT_PRENOM_SIGNATAIRE.toString())))
            .andExpect(jsonPath("$.[*].emailSignataire").value(hasItem(DEFAULT_EMAIL_SIGNATAIRE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())));
    }

    @Test
    @Transactional
    public void getSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);

        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", signature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(signature.getId().intValue()))
            .andExpect(jsonPath("$.nomSignataire").value(DEFAULT_NOM_SIGNATAIRE.toString()))
            .andExpect(jsonPath("$.prenomSignataire").value(DEFAULT_PRENOM_SIGNATAIRE.toString()))
            .andExpect(jsonPath("$.emailSignataire").value(DEFAULT_EMAIL_SIGNATAIRE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSignature() throws Exception {
        // Get the signature
        restSignatureMockMvc.perform(get("/api/signatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Update the signature
        Signature updatedSignature = signatureRepository.findOne(signature.getId());
        // Disconnect from session so that the updates on updatedSignature are not directly saved in db
        em.detach(updatedSignature);
        updatedSignature
            .nomSignataire(UPDATED_NOM_SIGNATAIRE)
            .prenomSignataire(UPDATED_PRENOM_SIGNATAIRE)
            .emailSignataire(UPDATED_EMAIL_SIGNATAIRE)
            .statut(UPDATED_STATUT);
        SignatureDTO signatureDTO = signatureMapper.toDto(updatedSignature);

        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isOk());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate);
        Signature testSignature = signatureList.get(signatureList.size() - 1);
        assertThat(testSignature.getNomSignataire()).isEqualTo(UPDATED_NOM_SIGNATAIRE);
        assertThat(testSignature.getPrenomSignataire()).isEqualTo(UPDATED_PRENOM_SIGNATAIRE);
        assertThat(testSignature.getEmailSignataire()).isEqualTo(UPDATED_EMAIL_SIGNATAIRE);
        assertThat(testSignature.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingSignature() throws Exception {
        int databaseSizeBeforeUpdate = signatureRepository.findAll().size();

        // Create the Signature
        SignatureDTO signatureDTO = signatureMapper.toDto(signature);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSignatureMockMvc.perform(put("/api/signatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signatureDTO)))
            .andExpect(status().isCreated());

        // Validate the Signature in the database
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSignature() throws Exception {
        // Initialize the database
        signatureRepository.saveAndFlush(signature);
        int databaseSizeBeforeDelete = signatureRepository.findAll().size();

        // Get the signature
        restSignatureMockMvc.perform(delete("/api/signatures/{id}", signature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Signature> signatureList = signatureRepository.findAll();
        assertThat(signatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Signature.class);
        Signature signature1 = new Signature();
        signature1.setId(1L);
        Signature signature2 = new Signature();
        signature2.setId(signature1.getId());
        assertThat(signature1).isEqualTo(signature2);
        signature2.setId(2L);
        assertThat(signature1).isNotEqualTo(signature2);
        signature1.setId(null);
        assertThat(signature1).isNotEqualTo(signature2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignatureDTO.class);
        SignatureDTO signatureDTO1 = new SignatureDTO();
        signatureDTO1.setId(1L);
        SignatureDTO signatureDTO2 = new SignatureDTO();
        assertThat(signatureDTO1).isNotEqualTo(signatureDTO2);
        signatureDTO2.setId(signatureDTO1.getId());
        assertThat(signatureDTO1).isEqualTo(signatureDTO2);
        signatureDTO2.setId(2L);
        assertThat(signatureDTO1).isNotEqualTo(signatureDTO2);
        signatureDTO1.setId(null);
        assertThat(signatureDTO1).isNotEqualTo(signatureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(signatureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(signatureMapper.fromId(null)).isNull();
    }
}
