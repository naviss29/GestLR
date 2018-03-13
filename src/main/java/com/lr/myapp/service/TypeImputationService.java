package com.lr.myapp.service;

import com.lr.myapp.service.dto.TypeImputationDTO;
import java.util.List;

/**
 * Service Interface for managing TypeImputation.
 */
public interface TypeImputationService {

    /**
     * Save a typeImputation.
     *
     * @param typeImputationDTO the entity to save
     * @return the persisted entity
     */
    TypeImputationDTO save(TypeImputationDTO typeImputationDTO);

    /**
     * Get all the typeImputations.
     *
     * @return the list of entities
     */
    List<TypeImputationDTO> findAll();

    /**
     * Get the "id" typeImputation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TypeImputationDTO findOne(Long id);

    /**
     * Delete the "id" typeImputation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
