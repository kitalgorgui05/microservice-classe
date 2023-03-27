package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.memoire.kital.raph.service.restClient.SalleClient;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Classe implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",updatable = false,nullable = false)
    private String id;
    @NotNull
    @Size(min = SizeMaper.SizeMaperClasse.MIN, max = SizeMaper.SizeMaperClasse.MAX)
    @Column(name = "nom", length = SizeMaper.SizeMaperClasse.MAX, nullable = false)
    private String nom;

    @ManyToOne
    @JsonIgnoreProperties(value = "classes", allowSetters = true)
    private Niveau niveau;

    @NotNull
    @Size(min = SizeMaper.SizeMaperClasse.MIN, max = SizeMaper.SizeMaperClasse.MAX)
    private String salle;

    @Transient
    private SalleClient salleClient;
}
