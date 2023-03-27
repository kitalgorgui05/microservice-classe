package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Classe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, String>, JpaSpecificationExecutor<Classe> {
    public List<Classe> getClasseByNiveau_Id(String id);
}
