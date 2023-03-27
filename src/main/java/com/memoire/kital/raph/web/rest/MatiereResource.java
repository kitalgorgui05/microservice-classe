package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.service.MatiereService;
import com.memoire.kital.raph.web.rest.errors.BadRequestAlertException;
import com.memoire.kital.raph.service.dto.MatiereDTO;
import com.memoire.kital.raph.service.dto.MatiereCriteria;
import com.memoire.kital.raph.service.MatiereQueryService;

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
public class MatiereResource {
    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);
    private static final String ENTITY_NAME = "classe1Matiere";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final MatiereService matiereService;
    private final MatiereQueryService matiereQueryService;

    public MatiereResource(MatiereService matiereService, MatiereQueryService matiereQueryService) {
        this.matiereService = matiereService;
        this.matiereQueryService = matiereQueryService;
    }
    @PostMapping("/matieres")
    public ResponseEntity<MatiereDTO> createMatiere(@Valid @RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to save Matiere : {}", matiereDTO);
        if (matiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new matiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.created(new URI("/api/matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNom().toString()))
            .body(result);
    }
    @PutMapping("/matieres")
    public ResponseEntity<MatiereDTO> updateMatiere(@Valid @RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to update Matiere : {}", matiereDTO);
        if (matiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, matiereDTO.getNom().toString()))
            .body(result);
    }
    @GetMapping("/matieres")
    public ResponseEntity<List<MatiereDTO>> getAllMatieres(MatiereCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Matieres by criteria: {}", criteria);
        Page<MatiereDTO> page = matiereQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/matieres/count")
    public ResponseEntity<Long> countMatieres(MatiereCriteria criteria) {
        log.debug("REST request to count Matieres by criteria: {}", criteria);
        return ResponseEntity.ok().body(matiereQueryService.countByCriteria(criteria));
    }
    @GetMapping("/matieres/{id}")
    public ResponseEntity<MatiereDTO> getMatiere(@PathVariable String id) {
        log.debug("REST request to get Matiere : {}", id);
        Optional<MatiereDTO> matiereDTO = matiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiereDTO);
    }
    @DeleteMapping("/matieres/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable String id) {
        log.debug("REST request to delete Matiere : {}", id);
        matiereService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
