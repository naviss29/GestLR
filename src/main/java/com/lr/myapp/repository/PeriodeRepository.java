package com.lr.myapp.repository;

import com.lr.myapp.domain.Periode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Periode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {

}
