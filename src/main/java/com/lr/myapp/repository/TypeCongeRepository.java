package com.lr.myapp.repository;

import com.lr.myapp.domain.TypeConge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeConge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCongeRepository extends JpaRepository<TypeConge, Long> {

}
