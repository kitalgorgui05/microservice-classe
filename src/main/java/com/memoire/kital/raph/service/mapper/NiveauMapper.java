package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.NiveauDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MatiereMapper.class})
public interface NiveauMapper extends EntityMapper<NiveauDTO, Niveau> {


    @Mapping(target = "removeMatiere", ignore = true)

    default Niveau fromId(String id) {
        if (id == null) {
            return null;
        }
        Niveau niveau = new Niveau();
        niveau.setId(id);
        return niveau;
    }
}
