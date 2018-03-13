package com.lr.myapp.service;

import com.lr.myapp.service.dto.PeriodeDTO;
import java.util.List;

/**
 * Service Interface for managing Periode.
 */
public interface PeriodeService {

    /**
     * Save a periode.
     *
     * @param periodeDTO the entity to save
     * @return the persisted entity
     */
    PeriodeDTO save(PeriodeDTO periodeDTO);

    /**
     * Get all the periodes.
     *
     * @return the list of entities
     */
    List<PeriodeDTO> findAll();

    /**
     * Get the "id" periode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PeriodeDTO findOne(Long id);

    /**
     * Delete the "id" periode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
