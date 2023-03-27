package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.openFeign.ISalle;
import com.memoire.kital.raph.service.ClasseService;
import com.memoire.kital.raph.domain.Classe;
import com.memoire.kital.raph.repository.ClasseRepository;
import com.memoire.kital.raph.service.dto.ClasseDTO;
import com.memoire.kital.raph.service.mapper.ClasseMapper;
import com.memoire.kital.raph.service.restClient.SalleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {
    private final Logger log = LoggerFactory.getLogger(ClasseServiceImpl.class);
    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;
    private final ISalle iSalle;

    public ClasseServiceImpl(ClasseRepository classeRepository, ClasseMapper classeMapper, ISalle iSalle) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.iSalle = iSalle;
    }

    @Override
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.saveAndFlush(classe);
        return classeMapper.toDto(classe);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
         Page<Classe> classes=classeRepository.findAll(pageable);
         return classes.map(classeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOne(String id) {
        log.debug("Request to get Classe : {}", id);
        Classe classe= classeRepository.findById(id).orElse(null);
        if (classe!=null){
            SalleClient salleClient= iSalle.getSalle(classe.getSalle()).getBody();
            classe.setSalleClient(salleClient);
        }
        return Optional.ofNullable(classeMapper.toDto(classe));
    }
    @Override
    public void delete(String id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.deleteById(id);
    }

    @Override
    public List<ClasseDTO> getClasseByNiveauId(String id) {
      return classeMapper.toDto(
          classeRepository.getClasseByNiveau_Id(id)
      );
    }
}
