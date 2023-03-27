package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


public class MatiereCriteria implements Serializable, Criteria {

    private StringFilter id;
    private StringFilter nom;
    private StringFilter niveauId;

    public MatiereCriteria() {
    }
    public MatiereCriteria(MatiereCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.niveauId = other.niveauId == null ? null : other.niveauId.copy();
    }

    @Override
    public MatiereCriteria copy() {
        return new MatiereCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getNiveauId() {
        return niveauId;
    }
    public void setNiveauId(StringFilter niveauId) {
        this.niveauId = niveauId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MatiereCriteria that = (MatiereCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(niveauId, that.niveauId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        niveauId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (niveauId != null ? "niveauId=" + niveauId + ", " : "") +
            "}";
    }

}
