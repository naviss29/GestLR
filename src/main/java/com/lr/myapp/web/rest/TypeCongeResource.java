package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.domain.TypeConge;

import com.lr.myapp.repository.TypeCongeRepository;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeConge.
 */
@RestController
@RequestMapping("/api")
public class TypeCongeResource {

    private final Logger log = LoggerFactory.getLogger(TypeCongeResource.class);

    private static final String ENTITY_NAME = "typeConge";

    private final TypeCongeRepository typeCongeRepository;

    public TypeCongeResource(TypeCongeRepository typeCongeRepository) {
        this.typeCongeRepository = typeCongeRepository;
    }

    /**
     * POST  /type-conges : Create a new typeConge.
     *
     * @param typeConge the typeConge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeConge, or with status 400 (Bad Request) if the typeConge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-conges")
    @Timed
    public ResponseEntity<TypeConge> createTypeConge(@RequestBody TypeConge typeConge) throws URISyntaxException {
        log.debug("REST request to save TypeConge : {}", typeConge);
        if (typeConge.getId() != null) {
            throw new BadRequestAlertException("A new typeConge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeConge result = typeCongeRepository.save(typeConge);
        return ResponseEntity.created(new URI("/api/type-conges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-conges : Updates an existing typeConge.
     *
     * @param typeConge the typeConge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeConge,
     * or with status 400 (Bad Request) if the typeConge is not valid,
     * or with status 500 (Internal Server Error) if the typeConge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-conges")
    @Timed
    public ResponseEntity<TypeConge> updateTypeConge(@RequestBody TypeConge typeConge) throws URISyntaxException {
        log.debug("REST request to update TypeConge : {}", typeConge);
        if (typeConge.getId() == null) {
            return createTypeConge(typeConge);
        }
        TypeConge result = typeCongeRepository.save(typeConge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeConge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-conges : get all the typeConges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeConges in body
     */
    @GetMapping("/type-conges")
    @Timed
    public List<TypeConge> getAllTypeConges() {
        log.debug("REST request to get all TypeConges");
        return typeCongeRepository.findAll();
        }

    /**
     * GET  /type-conges/:id : get the "id" typeConge.
     *
     * @param id the id of the typeConge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeConge, or with status 404 (Not Found)
     */
    @GetMapping("/type-conges/{id}")
    @Timed
    public ResponseEntity<TypeConge> getTypeConge(@PathVariable Long id) {
        log.debug("REST request to get TypeConge : {}", id);
        TypeConge typeConge = typeCongeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeConge));
    }

    /**
     * DELETE  /type-conges/:id : delete the "id" typeConge.
     *
     * @param id the id of the typeConge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-conges/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeConge(@PathVariable Long id) {
        log.debug("REST request to delete TypeConge : {}", id);
        typeCongeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
