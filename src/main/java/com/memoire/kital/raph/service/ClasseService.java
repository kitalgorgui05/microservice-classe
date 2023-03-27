package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ClasseDTO;

import com.memoire.kital.raph.service.restClient.SalleClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClasseService {
    ClasseDTO save(ClasseDTO classeDTO);
    Page<ClasseDTO> findAll(Pageable pageable);
    Optional<ClasseDTO> findOne(String id);
    void delete(String id);
    List<ClasseDTO> getClasseByNiveauId(String id);
}
