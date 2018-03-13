package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.TypeImputationService;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import com.lr.myapp.service.dto.TypeImputationDTO;
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
 * REST controller for managing TypeImputation.
 */
@RestController
@RequestMapping("/api")
public class TypeImputationResource {

    private final Logger log = LoggerFactory.getLogger(TypeImputationResource.class);

    private static final String ENTITY_NAME = "typeImputation";

    private final TypeImputationService typeImputationService;

    public TypeImputationResource(TypeImputationService typeImputationService) {
        this.typeImputationService = typeImputationService;
    }

    /**
     * POST  /type-imputations : Create a new typeImputation.
     *
     * @param typeImputationDTO the typeImputationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeImputationDTO, or with status 400 (Bad Request) if the typeImputation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-imputations")
    @Timed
    public ResponseEntity<TypeImputationDTO> createTypeImputation(@Valid @RequestBody TypeImputationDTO typeImputationDTO) throws URISyntaxException {
        log.debug("REST request to save TypeImputation : {}", typeImputationDTO);
        if (typeImputationDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeImputation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeImputationDTO result = typeImputationService.save(typeImputationDTO);
        return ResponseEntity.created(new URI("/api/type-imputations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-imputations : Updates an existing typeImputation.
     *
     * @param typeImputationDTO the typeImputationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeImputationDTO,
     * or with status 400 (Bad Request) if the typeImputationDTO is not valid,
     * or with status 500 (Internal Server Error) if the typeImputationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-imputations")
    @Timed
    public ResponseEntity<TypeImputationDTO> updateTypeImputation(@Valid @RequestBody TypeImputationDTO typeImputationDTO) throws URISyntaxException {
        log.debug("REST request to update TypeImputation : {}", typeImputationDTO);
        if (typeImputationDTO.getId() == null) {
            return createTypeImputation(typeImputationDTO);
        }
        TypeImputationDTO result = typeImputationService.save(typeImputationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeImputationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-imputations : get all the typeImputations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeImputations in body
     */
    @GetMapping("/type-imputations")
    @Timed
    public List<TypeImputationDTO> getAllTypeImputations() {
        log.debug("REST request to get all TypeImputations");
        return typeImputationService.findAll();
        }

    /**
     * GET  /type-imputations/:id : get the "id" typeImputation.
     *
     * @param id the id of the typeImputationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeImputationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/type-imputations/{id}")
    @Timed
    public ResponseEntity<TypeImputationDTO> getTypeImputation(@PathVariable Long id) {
        log.debug("REST request to get TypeImputation : {}", id);
        TypeImputationDTO typeImputationDTO = typeImputationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeImputationDTO));
    }

    /**
     * DELETE  /type-imputations/:id : delete the "id" typeImputation.
     *
     * @param id the id of the typeImputationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-imputations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeImputation(@PathVariable Long id) {
        log.debug("REST request to delete TypeImputation : {}", id);
        typeImputationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
