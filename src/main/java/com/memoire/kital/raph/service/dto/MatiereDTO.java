package com.memoire.kital.raph.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatiereDTO implements Serializable {
    private String id;
    private String nom;
}
