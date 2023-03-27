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

import com.memoire.kital.raph.domain.Matiere;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.MatiereRepository;
import com.memoire.kital.raph.service.dto.MatiereCriteria;
import com.memoire.kital.raph.service.dto.MatiereDTO;
import com.memoire.kital.raph.service.mapper.MatiereMapper;

@Service
@Transactional(readOnly = true)
public class MatiereQueryService extends QueryService<Matiere> {
    private final Logger log = LoggerFactory.getLogger(MatiereQueryService.class);
    private final MatiereRepository matiereRepository;
    private final MatiereMapper matiereMapper;
    public MatiereQueryService(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }
    @Transactional(readOnly = true)
    public List<MatiereDTO> findByCriteria(MatiereCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereMapper.toDto(matiereRepository.findAll(specification));
    }
    @Transactional(readOnly = true)
    public Page<MatiereDTO> findByCriteria(MatiereCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereRepository.findAll(specification, page)
            .map(matiereMapper::toDto);
    }
    @Transactional(readOnly = true)
    public long countByCriteria(MatiereCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereRepository.count(specification);
    }
    protected Specification<Matiere> createSpecification(MatiereCriteria criteria) {
        Specification<Matiere> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Matiere_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Matiere_.nom));
            }
            if (criteria.getNiveauId() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauId(),
                    root -> root.join(Matiere_.niveaus, JoinType.LEFT).get(Niveau_.id)));
            }
        }
        return specification;
    }
}
