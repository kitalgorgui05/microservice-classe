package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Matiere;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, String>, JpaSpecificationExecutor<Matiere> {
}
