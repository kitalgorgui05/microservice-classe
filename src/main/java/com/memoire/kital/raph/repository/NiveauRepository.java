package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Classe;
import com.memoire.kital.raph.domain.Niveau;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Niveau entity.
 */
@Repository
public interface NiveauRepository extends JpaRepository<Niveau, String>, JpaSpecificationExecutor<Niveau> {

    @Query(value = "select distinct niveau from Niveau niveau left join fetch niveau.matieres",
        countQuery = "select count(distinct niveau) from Niveau niveau")
    Page<Niveau> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct niveau from Niveau niveau left join fetch niveau.matieres")
    List<Niveau> findAllWithEagerRelationships();

    @Query("select niveau from Niveau niveau left join fetch niveau.matieres where niveau.id =:id")
    Optional<Niveau> findOneWithEagerRelationships(@Param("id") String id);
}
