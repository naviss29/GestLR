package com.lr.myapp.repository;

import com.lr.myapp.domain.Imputation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Imputation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImputationRepository extends JpaRepository<Imputation, Long> {

}
