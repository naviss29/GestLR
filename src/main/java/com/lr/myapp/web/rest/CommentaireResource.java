package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.CommentaireService;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import com.lr.myapp.service.dto.CommentaireDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/api")
public class CommentaireResource {

    private final Logger log = LoggerFactory.getLogger(CommentaireResource.class);

    private static final String ENTITY_NAME = "commentaire";

    private final CommentaireService commentaireService;

    public CommentaireResource(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     * POST  /commentaires : Create a new commentaire.
     *
     * @param commentaireDTO the commentaireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentaireDTO, or with status 400 (Bad Request) if the commentaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commentaires")
    @Timed
    public ResponseEntity<CommentaireDTO> createCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaireDTO);
        if (commentaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentaireDTO result = commentaireService.save(commentaireDTO);
        return ResponseEntity.created(new URI("/api/commentaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commentaires : Updates an existing commentaire.
     *
     * @param commentaireDTO the commentaireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentaireDTO,
     * or with status 400 (Bad Request) if the commentaireDTO is not valid,
     * or with status 500 (Internal Server Error) if the commentaireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commentaires")
    @Timed
    public ResponseEntity<CommentaireDTO> updateCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
        log.debug("REST request to update Commentaire : {}", commentaireDTO);
        if (commentaireDTO.getId() == null) {
            return createCommentaire(commentaireDTO);
        }
        CommentaireDTO result = commentaireService.save(commentaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commentaires : get all the commentaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commentaires in body
     */
    @GetMapping("/commentaires")
    @Timed
    public List<CommentaireDTO> getAllCommentaires() {
        log.debug("REST request to get all Commentaires");
        return commentaireService.findAll();
        }

    /**
     * GET  /commentaires/:id : get the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentaireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commentaires/{id}")
    @Timed
    public ResponseEntity<CommentaireDTO> getCommentaire(@PathVariable Long id) {
        log.debug("REST request to get Commentaire : {}", id);
        CommentaireDTO commentaireDTO = commentaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentaireDTO));
    }

    /**
     * DELETE  /commentaires/:id : delete the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commentaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        log.debug("REST request to delete Commentaire : {}", id);
        commentaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
