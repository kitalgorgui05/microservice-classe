package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.NiveauDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NiveauService {
    NiveauDTO save(NiveauDTO niveauDTO);
    Page<NiveauDTO> findAll(Pageable pageable);
    Page<NiveauDTO> findAllWithEagerRelationships(Pageable pageable);
    Optional<NiveauDTO> findOne(String id);
    void delete(String id);
}
