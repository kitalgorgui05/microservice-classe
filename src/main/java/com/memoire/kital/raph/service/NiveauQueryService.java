package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Niveau;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.NiveauRepository;
import com.memoire.kital.raph.service.dto.NiveauCriteria;
import com.memoire.kital.raph.service.dto.NiveauDTO;
import com.memoire.kital.raph.service.mapper.NiveauMapper;
@Service
@Transactional(readOnly = true)
public class NiveauQueryService extends QueryService<Niveau> {
    private final Logger log = LoggerFactory.getLogger(NiveauQueryService.class);
    private final NiveauRepository niveauRepository;
    private final NiveauMapper niveauMapper;
    public NiveauQueryService(NiveauRepository niveauRepository, NiveauMapper niveauMapper) {
        this.niveauRepository = niveauRepository;
        this.niveauMapper = niveauMapper;
    }

    @Transactional(readOnly = true)
    public List<NiveauDTO> findByCriteria(NiveauCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauMapper.toDto(niveauRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<NiveauDTO> findByCriteria(NiveauCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauRepository.findAll(specification, page)
            .map(niveauMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(NiveauCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauRepository.count(specification);
    }

    protected Specification<Niveau> createSpecification(NiveauCriteria criteria) {
        Specification<Niveau> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Niveau_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Niveau_.nom));
            }
            if (criteria.getMatiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatiereId(),
                    root -> root.join(Niveau_.matieres, JoinType.LEFT).get(Matiere_.id)));
            }
        }
        return specification;
    }
}
