package com.memoire.kital.raph.service.dto;

import com.memoire.kital.raph.domain.Classe;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NiveauDTO implements Serializable {
    private String id;
    private String nom;

    private Set<MatiereDTO> matieres = new HashSet<>();
   // private Set<ClasseDTO> classes = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<MatiereDTO> getMatieres() {
        return matieres;
    }

    public void setMatieres(Set<MatiereDTO> matieres) {
        this.matieres = matieres;
    }

    public NiveauDTO() {
    }

    public NiveauDTO(String id) {
        this.id = id;
    }


    public NiveauDTO(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NiveauDTO)) {
            return false;
        }

        return id != null && id.equals(((NiveauDTO) o).id);
    }


    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", matieres='" + getMatieres() + "'" +
            "}";
    }
}
