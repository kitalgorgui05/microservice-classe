package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.NiveauService;
import com.memoire.kital.raph.domain.Niveau;
import com.memoire.kital.raph.repository.NiveauRepository;
import com.memoire.kital.raph.service.dto.NiveauDTO;
import com.memoire.kital.raph.service.mapper.NiveauMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class NiveauServiceImpl implements NiveauService {
    private final Logger log = LoggerFactory.getLogger(NiveauServiceImpl.class);
    private final NiveauRepository niveauRepository;
    private final NiveauMapper niveauMapper;
    public NiveauServiceImpl(NiveauRepository niveauRepository, NiveauMapper niveauMapper) {
        this.niveauRepository = niveauRepository;
        this.niveauMapper = niveauMapper;
    }

    @Override
    public NiveauDTO save(NiveauDTO niveauDTO) {
        log.debug("Request to save Niveau : {}", niveauDTO);
        Niveau niveau = niveauMapper.toEntity(niveauDTO);
        niveau = niveauRepository.saveAndFlush(niveau);
        return niveauMapper.toDto(niveau);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NiveauDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Niveaus");
        return niveauRepository.findAll(pageable)
            .map(niveauMapper::toDto);
    }

    public Page<NiveauDTO> findAllWithEagerRelationships(Pageable pageable) {
        return niveauRepository.findAllWithEagerRelationships(pageable).map(niveauMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NiveauDTO> findOne(String id) {
        log.debug("Request to get Niveau : {}", id);
        return niveauRepository.findOneWithEagerRelationships(id)
            .map(niveauMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Niveau : {}", id);
        niveauRepository.deleteById(id);
    }
}
