package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.NiveauService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.NiveauDTO;
import com.memoire.kital.raph.service.dto.NiveauCriteria;
import com.memoire.kital.raph.service.NiveauQueryService;

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
public class NiveauResource {
    private final Logger log = LoggerFactory.getLogger(NiveauResource.class);
    private static final String ENTITY_NAME = "classe1Niveau";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final NiveauService niveauService;
    private final NiveauQueryService niveauQueryService;
    public NiveauResource(NiveauService niveauService, NiveauQueryService niveauQueryService) {
        this.niveauService = niveauService;
        this.niveauQueryService = niveauQueryService;
    }
    @PostMapping("/niveaus")
    public ResponseEntity<NiveauDTO> createNiveau(@Valid @RequestBody NiveauDTO niveauDTO) throws URISyntaxException {
        log.debug("REST request to save Niveau : {}", niveauDTO);
        if (niveauDTO.getId() != null) {
            throw new BadRequestAlertException("A new niveau cannot already have an ID", ENTITY_NAME, "id exists");
        }
        NiveauDTO result = niveauService.save(niveauDTO);
        return ResponseEntity.created(new URI("/api/niveaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNom().toString()))
            .body(result);
    }
    @PutMapping("/niveaus")
    public ResponseEntity<NiveauDTO> updateNiveau(@Valid @RequestBody NiveauDTO niveauDTO) throws URISyntaxException {
        log.debug("REST request to update Niveau : {}", niveauDTO);
        if (niveauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NiveauDTO result = niveauService.save(niveauDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveauDTO.getNom().toString()))
            .body(result);
    }
    @GetMapping("/niveaus")
    public ResponseEntity<List<NiveauDTO>> getAllNiveaus(NiveauCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Niveaus by criteria: {}", criteria);
        Page<NiveauDTO> page = niveauQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/niveaus/count")
    public ResponseEntity<Long> countNiveaus(NiveauCriteria criteria) {
        log.debug("REST request to count Niveaus by criteria: {}", criteria);
        return ResponseEntity.ok().body(niveauQueryService.countByCriteria(criteria));
    }
    @GetMapping("/niveaus/{id}")
    public ResponseEntity<NiveauDTO> getNiveau(@PathVariable String id) {
        log.debug("REST request to get Niveau : {}", id);
        Optional<NiveauDTO> niveauDTO = niveauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(niveauDTO);
    }
    @DeleteMapping("/niveaus/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable String id) {
        log.debug("REST request to delete Niveau : {}", id);
        niveauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
