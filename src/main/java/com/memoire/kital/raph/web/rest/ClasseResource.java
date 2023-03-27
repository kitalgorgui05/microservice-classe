package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.ClasseService;
import com.memoire.kital.raph.service.restClient.SalleClient;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.ClasseDTO;
import com.memoire.kital.raph.service.dto.ClasseCriteria;
import com.memoire.kital.raph.service.ClasseQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClasseResource {
    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);
    private static final String ENTITY_NAME = "classe1Classe";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ClasseService classeService;
    private final ClasseQueryService classeQueryService;
    public ClasseResource(ClasseService classeService, ClasseQueryService classeQueryService) {
        this.classeService = classeService;
        this.classeQueryService = classeQueryService;
    }
    @PostMapping("/classes")
    public ResponseEntity<ClasseDTO> createClasse(@Valid @RequestBody ClasseDTO classeDTO) throws URISyntaxException {
        log.debug("REST request to save Classe : {}", classeDTO);
        if (classeDTO.getId() != null) {
            throw new BadRequestAlertException("A new classe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasseDTO result = classeService.save(classeDTO);
        return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNom().toString()))
            .body(result);
    }
    @PutMapping("/classes")
    public ResponseEntity<ClasseDTO> updateClasse(@Valid @RequestBody ClasseDTO classeDTO) throws URISyntaxException {
        log.debug("REST request to update Classe : {}", classeDTO);
        if (classeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClasseDTO result = classeService.save(classeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeDTO.getNom().toString()))
            .body(result);
    }
    @GetMapping("/classes")
    public ResponseEntity<List<ClasseDTO>> getAllClasses(ClasseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Classes by criteria: {}", criteria);
        Page<ClasseDTO> page = classeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/classes/count")
    public ResponseEntity<Long> countClasses(ClasseCriteria criteria) {
        log.debug("REST request to count Classes by criteria: {}", criteria);
        return ResponseEntity.ok().body(classeQueryService.countByCriteria(criteria));
    }
    @GetMapping("/classes/{id}")
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable(name = "id") String id) {
        log.debug("REST request to get Classe : {}", id);
        Optional<ClasseDTO> classeDTO = classeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classeDTO);
    }
    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable(name = "id") String id) {
        log.debug("REST request to delete Classe : {}", id);
        classeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

}
