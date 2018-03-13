package com.lr.myapp.service;

import com.lr.myapp.service.dto.ImputationDTO;
import java.util.List;

/**
 * Service Interface for managing Imputation.
 */
public interface ImputationService {

    /**
     * Save a imputation.
     *
     * @param imputationDTO the entity to save
     * @return the persisted entity
     */
    ImputationDTO save(ImputationDTO imputationDTO);

    /**
     * Get all the imputations.
     *
     * @return the list of entities
     */
    List<ImputationDTO> findAll();

    /**
     * Get the "id" imputation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ImputationDTO findOne(Long id);

    /**
     * Delete the "id" imputation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
