package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.MatiereDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface MatiereMapper extends EntityMapper<MatiereDTO, Matiere> {


    //@Mapping(target = "niveaus", ignore = true)
    //@Mapping(target = "removeNiveau", ignore = true)
    Matiere toEntity(MatiereDTO matiereDTO);

    default Matiere fromId(String id) {
        if (id == null) {
            return null;
        }
        Matiere matiere = new Matiere();
        matiere.setId(id);
        return matiere;
    }
}
