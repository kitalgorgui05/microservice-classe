package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.MatiereDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MatiereService {
    MatiereDTO save(MatiereDTO matiereDTO);
    Page<MatiereDTO> findAll(Pageable pageable);
    Optional<MatiereDTO> findOne(String id);
    void delete(String id);
}
