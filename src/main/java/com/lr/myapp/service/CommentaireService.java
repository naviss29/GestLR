package com.lr.myapp.service;

import com.lr.myapp.service.dto.CommentaireDTO;
import java.util.List;

/**
 * Service Interface for managing Commentaire.
 */
public interface CommentaireService {

    /**
     * Save a commentaire.
     *
     * @param commentaireDTO the entity to save
     * @return the persisted entity
     */
    CommentaireDTO save(CommentaireDTO commentaireDTO);

    /**
     * Get all the commentaires.
     *
     * @return the list of entities
     */
    List<CommentaireDTO> findAll();

    /**
     * Get the "id" commentaire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CommentaireDTO findOne(Long id);

    /**
     * Delete the "id" commentaire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
