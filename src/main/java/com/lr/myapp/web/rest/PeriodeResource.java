package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.PeriodeService;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import com.lr.myapp.service.dto.PeriodeDTO;
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
 * REST controller for managing Periode.
 */
@RestController
@RequestMapping("/api")
public class PeriodeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodeResource.class);

    private static final String ENTITY_NAME = "periode";

    private final PeriodeService periodeService;

    public PeriodeResource(PeriodeService periodeService) {
        this.periodeService = periodeService;
    }

    /**
     * POST  /periodes : Create a new periode.
     *
     * @param periodeDTO the periodeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodeDTO, or with status 400 (Bad Request) if the periode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodes")
    @Timed
    public ResponseEntity<PeriodeDTO> createPeriode(@Valid @RequestBody PeriodeDTO periodeDTO) throws URISyntaxException {
        log.debug("REST request to save Periode : {}", periodeDTO);
        if (periodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new periode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity.created(new URI("/api/periodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodes : Updates an existing periode.
     *
     * @param periodeDTO the periodeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodeDTO,
     * or with status 400 (Bad Request) if the periodeDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodes")
    @Timed
    public ResponseEntity<PeriodeDTO> updatePeriode(@Valid @RequestBody PeriodeDTO periodeDTO) throws URISyntaxException {
        log.debug("REST request to update Periode : {}", periodeDTO);
        if (periodeDTO.getId() == null) {
            return createPeriode(periodeDTO);
        }
        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodes : get all the periodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodes in body
     */
    @GetMapping("/periodes")
    @Timed
    public List<PeriodeDTO> getAllPeriodes() {
        log.debug("REST request to get all Periodes");
        return periodeService.findAll();
        }

    /**
     * GET  /periodes/:id : get the "id" periode.
     *
     * @param id the id of the periodeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periodes/{id}")
    @Timed
    public ResponseEntity<PeriodeDTO> getPeriode(@PathVariable Long id) {
        log.debug("REST request to get Periode : {}", id);
        PeriodeDTO periodeDTO = periodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(periodeDTO));
    }

    /**
     * DELETE  /periodes/:id : delete the "id" periode.
     *
     * @param id the id of the periodeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodes/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriode(@PathVariable Long id) {
        log.debug("REST request to delete Periode : {}", id);
        periodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
