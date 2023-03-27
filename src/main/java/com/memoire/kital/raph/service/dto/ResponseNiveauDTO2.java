package com.memoire.kital.raph.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseNiveauDTO2 {
    private String id;
    private String nom;
    private Set<MatiereDTO> matieres = new HashSet<>();
}
