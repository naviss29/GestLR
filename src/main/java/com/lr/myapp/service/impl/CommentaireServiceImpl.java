package com.lr.myapp.service.impl;

import com.lr.myapp.service.CommentaireService;
import com.lr.myapp.domain.Commentaire;
import com.lr.myapp.repository.CommentaireRepository;
import com.lr.myapp.service.dto.CommentaireDTO;
import com.lr.myapp.service.mapper.CommentaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Commentaire.
 */
@Service
@Transactional
public class CommentaireServiceImpl implements CommentaireService {

    private final Logger log = LoggerFactory.getLogger(CommentaireServiceImpl.class);

    private final CommentaireRepository commentaireRepository;

    private final CommentaireMapper commentaireMapper;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository, CommentaireMapper commentaireMapper) {
        this.commentaireRepository = commentaireRepository;
        this.commentaireMapper = commentaireMapper;
    }

    /**
     * Save a commentaire.
     *
     * @param commentaireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentaireDTO save(CommentaireDTO commentaireDTO) {
        log.debug("Request to save Commentaire : {}", commentaireDTO);
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDTO);
        commentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(commentaire);
    }

    /**
     * Get all the commentaires.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentaireDTO> findAll() {
        log.debug("Request to get all Commentaires");
        return commentaireRepository.findAll().stream()
            .map(commentaireMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one commentaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentaireDTO findOne(Long id) {
        log.debug("Request to get Commentaire : {}", id);
        Commentaire commentaire = commentaireRepository.findOne(id);
        return commentaireMapper.toDto(commentaire);
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commentaire : {}", id);
        commentaireRepository.delete(id);
    }
}
