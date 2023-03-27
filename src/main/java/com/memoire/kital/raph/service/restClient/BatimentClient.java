package com.memoire.kital.raph.service.restClient;

public class BatimentClient {

    private String id;
    private String nom;
    private Integer nombreSalle;

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

    public Integer getNombreSalle() {
        return nombreSalle;
    }

    public void setNombreSalle(Integer nombreSalle) {
        this.nombreSalle = nombreSalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatimentClient)) {
            return false;
        }
        return id != null && id.equals(((BatimentClient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    @Override
    public String toString() {
        return "BatimentClient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreSalle=" + getNombreSalle() +
            "}";
    }
}
