package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.Classe1App;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Matiere;
import com.memoire.kital.raph.domain.Niveau;
import com.memoire.kital.raph.repository.MatiereRepository;
import com.memoire.kital.raph.service.MatiereService;
import com.memoire.kital.raph.service.dto.MatiereDTO;
import com.memoire.kital.raph.service.mapper.MatiereMapper;
import com.memoire.kital.raph.service.dto.MatiereCriteria;
import com.memoire.kital.raph.service.MatiereQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MatiereResource} REST controller.
 */
@SpringBootTest(classes = { Classe1App.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class MatiereResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private MatiereMapper matiereMapper;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private MatiereQueryService matiereQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereMockMvc;

    private Matiere matiere;
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .nom(DEFAULT_NOM);
        return matiere;
    }
    public static Matiere createUpdatedEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .nom(UPDATED_NOM);
        return matiere;
    }

    @BeforeEach
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();
        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);
        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(null);
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setNom(null);

        // Create the Matiere, which fails.
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);


        restMatiereMockMvc.perform(post("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getMatieresByIdFiltering() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        String id = matiere.getId();

        defaultMatiereShouldBeFound("id.equals=" + id);
        defaultMatiereShouldNotBeFound("id.notEquals=" + id);

        defaultMatiereShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatiereShouldNotBeFound("id.greaterThan=" + id);

        defaultMatiereShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatiereShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMatieresByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom equals to DEFAULT_NOM
        defaultMatiereShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the matiereList where nom equals to UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom not equals to DEFAULT_NOM
        defaultMatiereShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the matiereList where nom not equals to UPDATED_NOM
        defaultMatiereShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMatiereShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the matiereList where nom equals to UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom is not null
        defaultMatiereShouldBeFound("nom.specified=true");

        // Get all the matiereList where nom is null
        defaultMatiereShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMatieresByNomContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom contains DEFAULT_NOM
        defaultMatiereShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the matiereList where nom contains UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomNotContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom does not contain DEFAULT_NOM
        defaultMatiereShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the matiereList where nom does not contain UPDATED_NOM
        defaultMatiereShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMatieresByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);
        Niveau niveau = NiveauResourceIT.createEntity(em);
        em.persist(niveau);
        em.flush();
        matiere.addNiveau(niveau);
        matiereRepository.saveAndFlush(matiere);
        String niveauId = niveau.getId();

        // Get all the matiereList where niveau equals to niveauId
        defaultMatiereShouldBeFound("niveauId.equals=" + niveauId);

        // Get all the matiereList where niveau equals to niveauId + 1
        defaultMatiereShouldNotBeFound("niveauId.equals=" + (niveauId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatiereShouldBeFound(String filter) throws Exception {
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restMatiereMockMvc.perform(get("/api/matieres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatiereShouldNotBeFound(String filter) throws Exception {
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatiereMockMvc.perform(get("/api/matieres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findById(matiere.getId()).get();
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .nom(UPDATED_NOM);
        MatiereDTO matiereDTO = matiereMapper.toDto(updatedMatiere);

        restMatiereMockMvc.perform(put("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereMockMvc.perform(put("/api/matieres").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Delete the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
