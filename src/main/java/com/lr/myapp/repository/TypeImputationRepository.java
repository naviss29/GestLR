package com.lr.myapp.repository;

import com.lr.myapp.domain.TypeImputation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeImputation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeImputationRepository extends JpaRepository<TypeImputation, Long> {

    TypeImputation findByCode(@Param("code") String code);
}
