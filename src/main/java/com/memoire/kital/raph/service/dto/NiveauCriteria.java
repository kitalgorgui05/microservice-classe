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

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Niveau} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.NiveauResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /niveaus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NiveauCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter nom;
    private StringFilter matiereId;
    public NiveauCriteria() {
    }

    public NiveauCriteria(NiveauCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.matiereId = other.matiereId == null ? null : other.matiereId.copy();
    }

    @Override
    public NiveauCriteria copy() {
        return new NiveauCriteria(this);
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

    public StringFilter getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(StringFilter matiereId) {
        this.matiereId = matiereId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NiveauCriteria that = (NiveauCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(matiereId, that.matiereId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        matiereId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (matiereId != null ? "matiereId=" + matiereId + ", " : "") +
            "}";
    }

}
