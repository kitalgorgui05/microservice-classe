package com.memoire.kital.raph.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import com.memoire.kital.raph.openFeign.ISalle;
import com.memoire.kital.raph.service.restClient.SalleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.memoire.kital.raph.domain.Classe;
import com.memoire.kital.raph.domain.*; // for static metamodels
import com.memoire.kital.raph.repository.ClasseRepository;
import com.memoire.kital.raph.service.dto.ClasseCriteria;
import com.memoire.kital.raph.service.dto.ClasseDTO;
import com.memoire.kital.raph.service.mapper.ClasseMapper;

@Service
@Transactional(readOnly = true)
public class ClasseQueryService extends QueryService<Classe> {
    private final Logger log = LoggerFactory.getLogger(ClasseQueryService.class);
    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;
    private final ISalle iSalle;

    public ClasseQueryService(ClasseRepository classeRepository, ClasseMapper classeMapper, ISalle iSalle) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.iSalle = iSalle;
    }

    @Transactional(readOnly = true)
    public List<ClasseDTO> findByCriteria(ClasseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        List<Classe> classesList = classeRepository.findAll(specification);
        for (Classe c :classesList){
            SalleClient salleClient= iSalle.getSalle(c.getSalle()).getBody();
            c.setSalleClient(salleClient);
        }
        return classeMapper.toDto(classesList);
    }
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findByCriteria(ClasseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Classe> specification = createSpecification(criteria);
        Page<Classe> classePage = classeRepository.findAll(specification, page);
        for (Classe c :classePage.getContent()){
            SalleClient salleClient= iSalle.getSalle(c.getSalle()).getBody();
            c.setSalleClient(salleClient);
        }
        return classePage
            .map(classeMapper::toDto);
    }
    @Transactional(readOnly = true)
    public long countByCriteria(ClasseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeRepository.count(specification);
    }
    protected Specification<Classe> createSpecification(ClasseCriteria criteria) {
        Specification<Classe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), Classe_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Classe_.nom));
            }

            if (criteria.getNiveauId() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauId(),
                    root -> root.join(Classe_.niveau, JoinType.LEFT).get(Niveau_.id)));
            }
        }
        return specification;
    }
}
