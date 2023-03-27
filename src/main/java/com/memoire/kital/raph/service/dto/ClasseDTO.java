package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.service.restClient.SalleClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasseDTO implements Serializable {
    private String id;
    private String nom;
    private NiveauDTO niveau;
    private String salle;
    private SalleClient salleClient;
}
