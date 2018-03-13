package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.SignatureService;
import com.lr.myapp.web.rest.errors.BadRequestAlertException;
import com.lr.myapp.web.rest.util.HeaderUtil;
import com.lr.myapp.service.dto.SignatureDTO;
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
 * REST controller for managing Signature.
 */
@RestController
@RequestMapping("/api")
public class SignatureResource {

    private final Logger log = LoggerFactory.getLogger(SignatureResource.class);

    private static final String ENTITY_NAME = "signature";

    private final SignatureService signatureService;

    public SignatureResource(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    /**
     * POST  /signatures : Create a new signature.
     *
     * @param signatureDTO the signatureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signatureDTO, or with status 400 (Bad Request) if the signature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signatures")
    @Timed
    public ResponseEntity<SignatureDTO> createSignature(@Valid @RequestBody SignatureDTO signatureDTO) throws URISyntaxException {
        log.debug("REST request to save Signature : {}", signatureDTO);
        if (signatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new signature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignatureDTO result = signatureService.save(signatureDTO);
        return ResponseEntity.created(new URI("/api/signatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signatures : Updates an existing signature.
     *
     * @param signatureDTO the signatureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signatureDTO,
     * or with status 400 (Bad Request) if the signatureDTO is not valid,
     * or with status 500 (Internal Server Error) if the signatureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signatures")
    @Timed
    public ResponseEntity<SignatureDTO> updateSignature(@Valid @RequestBody SignatureDTO signatureDTO) throws URISyntaxException {
        log.debug("REST request to update Signature : {}", signatureDTO);
        if (signatureDTO.getId() == null) {
            return createSignature(signatureDTO);
        }
        SignatureDTO result = signatureService.save(signatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, signatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signatures : get all the signatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of signatures in body
     */
    @GetMapping("/signatures")
    @Timed
    public List<SignatureDTO> getAllSignatures() {
        log.debug("REST request to get all Signatures");
        return signatureService.findAll();
        }

    /**
     * GET  /signatures/:id : get the "id" signature.
     *
     * @param id the id of the signatureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signatureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<SignatureDTO> getSignature(@PathVariable Long id) {
        log.debug("REST request to get Signature : {}", id);
        SignatureDTO signatureDTO = signatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(signatureDTO));
    }

    /**
     * DELETE  /signatures/:id : delete the "id" signature.
     *
     * @param id the id of the signatureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        log.debug("REST request to delete Signature : {}", id);
        signatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
