package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.ImputationService;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import com.lr.myapp.service.dto.ImputationDTO;
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
 * REST controller for managing Imputation.
 */
@RestController
@RequestMapping("/api")
public class ImputationResource {

    private final Logger log = LoggerFactory.getLogger(ImputationResource.class);

    private static final String ENTITY_NAME = "imputation";

    private final ImputationService imputationService;

    public ImputationResource(ImputationService imputationService) {
        this.imputationService = imputationService;
    }

    /**
     * POST  /imputations : Create a new imputation.
     *
     * @param imputationDTO the imputationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imputationDTO, or with status 400 (Bad Request) if the imputation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/imputations")
    @Timed
    public ResponseEntity<ImputationDTO> createImputation(@Valid @RequestBody ImputationDTO imputationDTO) throws URISyntaxException {
        log.debug("REST request to save Imputation : {}", imputationDTO);
        if (imputationDTO.getId() != null) {
            throw new BadRequestAlertException("A new imputation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImputationDTO result = imputationService.save(imputationDTO);
        return ResponseEntity.created(new URI("/api/imputations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imputations : Updates an existing imputation.
     *
     * @param imputationDTO the imputationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imputationDTO,
     * or with status 400 (Bad Request) if the imputationDTO is not valid,
     * or with status 500 (Internal Server Error) if the imputationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/imputations")
    @Timed
    public ResponseEntity<ImputationDTO> updateImputation(@Valid @RequestBody ImputationDTO imputationDTO) throws URISyntaxException {
        log.debug("REST request to update Imputation : {}", imputationDTO);
        if (imputationDTO.getId() == null) {
            return createImputation(imputationDTO);
        }
        ImputationDTO result = imputationService.save(imputationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imputationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imputations : get all the imputations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imputations in body
     */
    @GetMapping("/imputations")
    @Timed
    public List<ImputationDTO> getAllImputations() {
        log.debug("REST request to get all Imputations");
        return imputationService.findAll();
        }

    /**
     * GET  /imputations/:id : get the "id" imputation.
     *
     * @param id the id of the imputationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imputationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/imputations/{id}")
    @Timed
    public ResponseEntity<ImputationDTO> getImputation(@PathVariable Long id) {
        log.debug("REST request to get Imputation : {}", id);
        ImputationDTO imputationDTO = imputationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imputationDTO));
    }

    /**
     * DELETE  /imputations/:id : delete the "id" imputation.
     *
     * @param id the id of the imputationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/imputations/{id}")
    @Timed
    public ResponseEntity<Void> deleteImputation(@PathVariable Long id) {
        log.debug("REST request to delete Imputation : {}", id);
        imputationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
