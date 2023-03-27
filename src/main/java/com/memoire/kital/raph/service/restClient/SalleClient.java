package com.memoire.kital.raph.service.restClient;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SalleClient implements Serializable {

    private String id;
    private String nom;
    private BatimentClient batiment;

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

    public BatimentClient getBatiment() {
        return batiment;
    }

    public void setBatiment(BatimentClient batiment) {
        this.batiment = batiment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalleClient)) {
            return false;
        }

        return id != null && id.equals(((SalleClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
