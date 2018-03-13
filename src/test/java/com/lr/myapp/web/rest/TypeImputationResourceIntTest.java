package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.TypeImputation;
import com.lr.myapp.repository.TypeImputationRepository;
import com.lr.myapp.service.TypeImputationService;
import com.lr.myapp.service.dto.TypeImputationDTO;
import com.lr.myapp.service.mapper.TypeImputationMapper;
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
 * Test class for the TypeImputationResource REST controller.
 *
 * @see TypeImputationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class TypeImputationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeImputationRepository typeImputationRepository;

    @Autowired
    private TypeImputationMapper typeImputationMapper;

    @Autowired
    private TypeImputationService typeImputationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeImputationMockMvc;

    private TypeImputation typeImputation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeImputationResource typeImputationResource = new TypeImputationResource(typeImputationService);
        this.restTypeImputationMockMvc = MockMvcBuilders.standaloneSetup(typeImputationResource)
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
    public static TypeImputation createEntity(EntityManager em) {
        TypeImputation typeImputation = new TypeImputation()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return typeImputation;
    }

    @Before
    public void initTest() {
        typeImputation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeImputation() throws Exception {
        int databaseSizeBeforeCreate = typeImputationRepository.findAll().size();

        // Create the TypeImputation
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);
        restTypeImputationMockMvc.perform(post("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeImputation in the database
        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeImputation testTypeImputation = typeImputationList.get(typeImputationList.size() - 1);
        assertThat(testTypeImputation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeImputation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeImputationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeImputationRepository.findAll().size();

        // Create the TypeImputation with an existing ID
        typeImputation.setId(1L);
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeImputationMockMvc.perform(post("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeImputation in the database
        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeImputationRepository.findAll().size();
        // set the field null
        typeImputation.setCode(null);

        // Create the TypeImputation, which fails.
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);

        restTypeImputationMockMvc.perform(post("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isBadRequest());

        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeImputationRepository.findAll().size();
        // set the field null
        typeImputation.setLibelle(null);

        // Create the TypeImputation, which fails.
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);

        restTypeImputationMockMvc.perform(post("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isBadRequest());

        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeImputations() throws Exception {
        // Initialize the database
        typeImputationRepository.saveAndFlush(typeImputation);

        // Get all the typeImputationList
        restTypeImputationMockMvc.perform(get("/api/type-imputations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeImputation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getTypeImputation() throws Exception {
        // Initialize the database
        typeImputationRepository.saveAndFlush(typeImputation);

        // Get the typeImputation
        restTypeImputationMockMvc.perform(get("/api/type-imputations/{id}", typeImputation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeImputation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeImputation() throws Exception {
        // Get the typeImputation
        restTypeImputationMockMvc.perform(get("/api/type-imputations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeImputation() throws Exception {
        // Initialize the database
        typeImputationRepository.saveAndFlush(typeImputation);
        int databaseSizeBeforeUpdate = typeImputationRepository.findAll().size();

        // Update the typeImputation
        TypeImputation updatedTypeImputation = typeImputationRepository.findOne(typeImputation.getId());
        // Disconnect from session so that the updates on updatedTypeImputation are not directly saved in db
        em.detach(updatedTypeImputation);
        updatedTypeImputation
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(updatedTypeImputation);

        restTypeImputationMockMvc.perform(put("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isOk());

        // Validate the TypeImputation in the database
        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeUpdate);
        TypeImputation testTypeImputation = typeImputationList.get(typeImputationList.size() - 1);
        assertThat(testTypeImputation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeImputation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeImputation() throws Exception {
        int databaseSizeBeforeUpdate = typeImputationRepository.findAll().size();

        // Create the TypeImputation
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeImputationMockMvc.perform(put("/api/type-imputations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeImputationDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeImputation in the database
        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeImputation() throws Exception {
        // Initialize the database
        typeImputationRepository.saveAndFlush(typeImputation);
        int databaseSizeBeforeDelete = typeImputationRepository.findAll().size();

        // Get the typeImputation
        restTypeImputationMockMvc.perform(delete("/api/type-imputations/{id}", typeImputation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeImputation> typeImputationList = typeImputationRepository.findAll();
        assertThat(typeImputationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeImputation.class);
        TypeImputation typeImputation1 = new TypeImputation();
        typeImputation1.setId(1L);
        TypeImputation typeImputation2 = new TypeImputation();
        typeImputation2.setId(typeImputation1.getId());
        assertThat(typeImputation1).isEqualTo(typeImputation2);
        typeImputation2.setId(2L);
        assertThat(typeImputation1).isNotEqualTo(typeImputation2);
        typeImputation1.setId(null);
        assertThat(typeImputation1).isNotEqualTo(typeImputation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeImputationDTO.class);
        TypeImputationDTO typeImputationDTO1 = new TypeImputationDTO();
        typeImputationDTO1.setId(1L);
        TypeImputationDTO typeImputationDTO2 = new TypeImputationDTO();
        assertThat(typeImputationDTO1).isNotEqualTo(typeImputationDTO2);
        typeImputationDTO2.setId(typeImputationDTO1.getId());
        assertThat(typeImputationDTO1).isEqualTo(typeImputationDTO2);
        typeImputationDTO2.setId(2L);
        assertThat(typeImputationDTO1).isNotEqualTo(typeImputationDTO2);
        typeImputationDTO1.setId(null);
        assertThat(typeImputationDTO1).isNotEqualTo(typeImputationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeImputationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeImputationMapper.fromId(null)).isNull();
    }
}
