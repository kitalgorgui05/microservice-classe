package com.memoire.kital.raph.service.mapper;

import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.ClasseDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {NiveauMapper.class})
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {
    //@Mapping(source = "niveau.id", target = "niveauId")
    ClasseDTO toDto(Classe classe);
    //@Mapping(source = "niveauId", target = "niveau")
    Classe toEntity(ClasseDTO classeDTO);

    default Classe fromId(String id) {
        if (id == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(id);
        return classe;
    }
}
