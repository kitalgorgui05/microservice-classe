package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.Classe1App;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Niveau;
import com.memoire.kital.raph.domain.Matiere;
import com.memoire.kital.raph.repository.NiveauRepository;
import com.memoire.kital.raph.service.NiveauService;
import com.memoire.kital.raph.service.dto.NiveauDTO;
import com.memoire.kital.raph.service.mapper.NiveauMapper;
import com.memoire.kital.raph.service.dto.NiveauCriteria;
import com.memoire.kital.raph.service.NiveauQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NiveauResource} REST controller.
 */
@SpringBootTest(classes = { Classe1App.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NiveauResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private NiveauRepository niveauRepository;

    @Mock
    private NiveauRepository niveauRepositoryMock;

    @Autowired
    private NiveauMapper niveauMapper;

    @Mock
    private NiveauService niveauServiceMock;

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private NiveauQueryService niveauQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauMockMvc;

    private Niveau niveau;
    public static Niveau createEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .nom(DEFAULT_NOM);
        // Add required entity
        Matiere matiere;
        if (TestUtil.findAll(em, Matiere.class).isEmpty()) {
            matiere = MatiereResourceIT.createEntity(em);
            em.persist(matiere);
            em.flush();
        } else {
            matiere = TestUtil.findAll(em, Matiere.class).get(0);
        }
        niveau.getMatieres().add(matiere);
        return niveau;
    }
    public static Niveau createUpdatedEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .nom(UPDATED_NOM);
        // Add required entity
        Matiere matiere;
        if (TestUtil.findAll(em, Matiere.class).isEmpty()) {
            matiere = MatiereResourceIT.createUpdatedEntity(em);
            em.persist(matiere);
            em.flush();
        } else {
            matiere = TestUtil.findAll(em, Matiere.class).get(0);
        }
        niveau.getMatieres().add(matiere);
        return niveau;
    }

    @BeforeEach
    public void initTest() {
        niveau = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiveau() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();
        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);
        restNiveauMockMvc.perform(post("/api/niveaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isCreated());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate + 1);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createNiveauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();

        // Create the Niveau with an existing ID
        niveau.setId(null);
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauMockMvc.perform(post("/api/niveaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = niveauRepository.findAll().size();
        // set the field null
        niveau.setNom(null);

        // Create the Niveau, which fails.
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);


        restNiveauMockMvc.perform(post("/api/niveaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNiveaus() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNiveausWithEagerRelationshipsIsEnabled() throws Exception {
        when(niveauServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNiveauMockMvc.perform(get("/api/niveaus?eagerload=true"))
            .andExpect(status().isOk());

        verify(niveauServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNiveausWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(niveauServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNiveauMockMvc.perform(get("/api/niveaus?eagerload=true"))
            .andExpect(status().isOk());

        verify(niveauServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", niveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveau.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getNiveausByIdFiltering() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        String id = niveau.getId();

        defaultNiveauShouldBeFound("id.equals=" + id);
        defaultNiveauShouldNotBeFound("id.notEquals=" + id);

        defaultNiveauShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.greaterThan=" + id);

        defaultNiveauShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNiveausByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom equals to DEFAULT_NOM
        defaultNiveauShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the niveauList where nom equals to UPDATED_NOM
        defaultNiveauShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllNiveausByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom not equals to DEFAULT_NOM
        defaultNiveauShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the niveauList where nom not equals to UPDATED_NOM
        defaultNiveauShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllNiveausByNomIsInShouldWork() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultNiveauShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the niveauList where nom equals to UPDATED_NOM
        defaultNiveauShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllNiveausByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom is not null
        defaultNiveauShouldBeFound("nom.specified=true");

        // Get all the niveauList where nom is null
        defaultNiveauShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllNiveausByNomContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom contains DEFAULT_NOM
        defaultNiveauShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the niveauList where nom contains UPDATED_NOM
        defaultNiveauShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllNiveausByNomNotContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where nom does not contain DEFAULT_NOM
        defaultNiveauShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the niveauList where nom does not contain UPDATED_NOM
        defaultNiveauShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllNiveausByMatiereIsEqualToSomething() throws Exception {
        // Get already existing entity
        Matiere matiere = (Matiere) niveau.getMatieres();
        niveauRepository.saveAndFlush(niveau);
        String matiereId = matiere.getId();

        // Get all the niveauList where matiere equals to matiereId
        defaultNiveauShouldBeFound("matiereId.equals=" + matiereId);

        // Get all the niveauList where matiere equals to matiereId + 1
        defaultNiveauShouldNotBeFound("matiereId.equals=" + (matiereId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNiveauShouldBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNiveauShouldNotBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNiveau() throws Exception {
        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Update the niveau
        Niveau updatedNiveau = niveauRepository.findById(niveau.getId()).get();
        // Disconnect from session so that the updates on updatedNiveau are not directly saved in db
        em.detach(updatedNiveau);
        updatedNiveau
            .nom(UPDATED_NOM);
        NiveauDTO niveauDTO = niveauMapper.toDto(updatedNiveau);

        restNiveauMockMvc.perform(put("/api/niveaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isOk());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingNiveau() throws Exception {
        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauMockMvc.perform(put("/api/niveaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeDelete = niveauRepository.findAll().size();

        // Delete the niveau
        restNiveauMockMvc.perform(delete("/api/niveaus/{id}", niveau.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
