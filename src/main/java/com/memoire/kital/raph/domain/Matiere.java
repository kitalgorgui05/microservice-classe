package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.memoire.kital.raph.utils.SizeMaper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Matiere.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matieres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matiere implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",updatable = false,nullable = false)
    private String id;

    @NotNull
    @Size(min = SizeMaper.SizeMapperMatiere.MIN, max = SizeMaper.SizeMapperMatiere.MAX)
    @Column(name = "nom", length = SizeMaper.SizeMapperMatiere.MAX, nullable = false, unique = true)
    private String nom;

    @ManyToMany(mappedBy = "matieres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Niveau> niveaus = new HashSet<>();
}
