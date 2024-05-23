package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.utils.SizeMaper;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Niveau.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "niveaus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Niveau implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",updatable = false,nullable = false)
    private String id;

    @NotNull
    @Size(min = SizeMaper.SizeMapperNiveau.MIN, max = SizeMaper.SizeMapperNiveau.MAX)
    @Column(name = "nom", length = SizeMaper.SizeMapperNiveau.MAX, nullable = false, unique = true)
    private String nom;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "niveaus_matiere",
               joinColumns = @JoinColumn(name = "niveau_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"))
    private Set<Matiere> matieres = new HashSet<>();

    /*@OneToMany(fetch = FetchType.EAGER, mappedBy = "niveau")
    private Set<Classe> classes = new HashSet<>();
*/
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Niveau nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public Niveau matieres(Set<Matiere> matieres) {
        this.matieres = matieres;
        return this;
    }

    public Niveau addMatiere(Matiere matiere) {
        this.matieres.add(matiere);
       // matiere.getNiveaus().add(this);
        return this;
    }

    public Niveau removeMatiere(Matiere matiere) {
        this.matieres.remove(matiere);
        //matiere.getNiveaus().remove(this);
        return this;
    }

    public void setMatieres(Set<Matiere> matieres) {
        this.matieres = matieres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Niveau)) {
            return false;
        }
        return id != null && id.equals(((Niveau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Niveau{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
