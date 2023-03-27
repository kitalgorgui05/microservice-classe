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

public class ClasseCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter nom;
    private StringFilter niveauId;
    private StringFilter salleId;

    public ClasseCriteria() {
    }

    public ClasseCriteria(ClasseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.niveauId = other.niveauId == null ? null : other.niveauId.copy();
        this.salleId=other.salleId==null ? null :other.salleId.copy();
    }

    @Override
    public ClasseCriteria copy() {
        return new ClasseCriteria(this);
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

    public StringFilter getSalleId() {
        return salleId;
    }

    public void setSalleId(StringFilter salleId) {
        this.salleId = salleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClasseCriteria that = (ClasseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(niveauId, that.niveauId) &&
            Objects.equals(salleId, that.salleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        niveauId,
        salleId
        );
    }

    @Override
    public String toString() {
        return "ClasseCriteria {" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (niveauId != null ? "niveau=" + niveauId + ", " : "") +
                (salleId != null ? "classe=" + salleId + ", " : "") +
            "}";
    }
}
