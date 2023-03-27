package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.MatiereService;
import com.memoire.kital.raph.domain.Matiere;
import com.memoire.kital.raph.repository.MatiereRepository;
import com.memoire.kital.raph.service.dto.MatiereDTO;
import com.memoire.kital.raph.service.mapper.MatiereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {
    private final Logger log = LoggerFactory.getLogger(MatiereServiceImpl.class);
    private final MatiereRepository matiereRepository;
    private final MatiereMapper matiereMapper;
    public MatiereServiceImpl(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }

    @Override
    public MatiereDTO save(MatiereDTO matiereDTO) {
        log.debug("Request to save Matiere : {}", matiereDTO);
        Matiere matiere = matiereMapper.toEntity(matiereDTO);
        matiere = matiereRepository.saveAndFlush(matiere);
        return matiereMapper.toDto(matiere);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatiereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matieres");
        return matiereRepository.findAll(pageable)
            .map(matiereMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatiereDTO> findOne(String id) {
        log.debug("Request to get Matiere : {}", id);
        return matiereRepository.findById(id)
            .map(matiereMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Matiere : {}", id);
        matiereRepository.deleteById(id);
    }
}
