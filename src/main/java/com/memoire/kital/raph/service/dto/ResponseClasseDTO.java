package com.memoire.kital.raph.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClasseDTO {
    private String id;
    private String nom;
    private NiveauDTO niveau;
}
