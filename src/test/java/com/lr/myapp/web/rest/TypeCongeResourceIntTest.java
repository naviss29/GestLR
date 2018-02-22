package com.lr.myapp.web.rest;

import com.lr.myapp.GestLrApp;

import com.lr.myapp.domain.TypeConge;
import com.lr.myapp.repository.TypeCongeRepository;
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
 * Test class for the TypeCongeResource REST controller.
 *
 * @see TypeCongeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestLrApp.class)
public class TypeCongeResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private TypeCongeRepository typeCongeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeCongeMockMvc;

    private TypeConge typeConge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCongeResource typeCongeResource = new TypeCongeResource(typeCongeRepository);
        this.restTypeCongeMockMvc = MockMvcBuilders.standaloneSetup(typeCongeResource)
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
    public static TypeConge createEntity(EntityManager em) {
        TypeConge typeConge = new TypeConge()
            .nom(DEFAULT_NOM);
        return typeConge;
    }

    @Before
    public void initTest() {
        typeConge = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeConge() throws Exception {
        int databaseSizeBeforeCreate = typeCongeRepository.findAll().size();

        // Create the TypeConge
        restTypeCongeMockMvc.perform(post("/api/type-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeConge)))
            .andExpect(status().isCreated());

        // Validate the TypeConge in the database
        List<TypeConge> typeCongeList = typeCongeRepository.findAll();
        assertThat(typeCongeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeConge testTypeConge = typeCongeList.get(typeCongeList.size() - 1);
        assertThat(testTypeConge.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createTypeCongeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCongeRepository.findAll().size();

        // Create the TypeConge with an existing ID
        typeConge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCongeMockMvc.perform(post("/api/type-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeConge)))
            .andExpect(status().isBadRequest());

        // Validate the TypeConge in the database
        List<TypeConge> typeCongeList = typeCongeRepository.findAll();
        assertThat(typeCongeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeConges() throws Exception {
        // Initialize the database
        typeCongeRepository.saveAndFlush(typeConge);

        // Get all the typeCongeList
        restTypeCongeMockMvc.perform(get("/api/type-conges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeConge.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getTypeConge() throws Exception {
        // Initialize the database
        typeCongeRepository.saveAndFlush(typeConge);

        // Get the typeConge
        restTypeCongeMockMvc.perform(get("/api/type-conges/{id}", typeConge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeConge.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeConge() throws Exception {
        // Get the typeConge
        restTypeCongeMockMvc.perform(get("/api/type-conges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeConge() throws Exception {
        // Initialize the database
        typeCongeRepository.saveAndFlush(typeConge);
        int databaseSizeBeforeUpdate = typeCongeRepository.findAll().size();

        // Update the typeConge
        TypeConge updatedTypeConge = typeCongeRepository.findOne(typeConge.getId());
        // Disconnect from session so that the updates on updatedTypeConge are not directly saved in db
        em.detach(updatedTypeConge);
        updatedTypeConge
            .nom(UPDATED_NOM);

        restTypeCongeMockMvc.perform(put("/api/type-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeConge)))
            .andExpect(status().isOk());

        // Validate the TypeConge in the database
        List<TypeConge> typeCongeList = typeCongeRepository.findAll();
        assertThat(typeCongeList).hasSize(databaseSizeBeforeUpdate);
        TypeConge testTypeConge = typeCongeList.get(typeCongeList.size() - 1);
        assertThat(testTypeConge.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeConge() throws Exception {
        int databaseSizeBeforeUpdate = typeCongeRepository.findAll().size();

        // Create the TypeConge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeCongeMockMvc.perform(put("/api/type-conges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeConge)))
            .andExpect(status().isCreated());

        // Validate the TypeConge in the database
        List<TypeConge> typeCongeList = typeCongeRepository.findAll();
        assertThat(typeCongeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeConge() throws Exception {
        // Initialize the database
        typeCongeRepository.saveAndFlush(typeConge);
        int databaseSizeBeforeDelete = typeCongeRepository.findAll().size();

        // Get the typeConge
        restTypeCongeMockMvc.perform(delete("/api/type-conges/{id}", typeConge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeConge> typeCongeList = typeCongeRepository.findAll();
        assertThat(typeCongeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeConge.class);
        TypeConge typeConge1 = new TypeConge();
        typeConge1.setId(1L);
        TypeConge typeConge2 = new TypeConge();
        typeConge2.setId(typeConge1.getId());
        assertThat(typeConge1).isEqualTo(typeConge2);
        typeConge2.setId(2L);
        assertThat(typeConge1).isNotEqualTo(typeConge2);
        typeConge1.setId(null);
        assertThat(typeConge1).isNotEqualTo(typeConge2);
    }
}
