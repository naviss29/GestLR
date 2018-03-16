package com.lr.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lr.myapp.service.InitImputationService;
import com.lr.myapp.service.PeriodeService;
import com.lr.myapp.service.dto.LigneImputationTypeDTO;
import com.lr.myapp.service.dto.PeriodeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing Periode.
 */
@RestController
@RequestMapping("/api")
public class InitImputationResource {

    private final Logger log = LoggerFactory.getLogger(InitImputationResource.class);

    private final InitImputationService initImputationService;

    public InitImputationResource(InitImputationService initImputationService) {
        this.initImputationService = initImputationService;
    }

    @GetMapping("/periodes/{id}/type/{type}")
    @Timed
    public ResponseEntity<LigneImputationTypeDTO> getNouvelleLigneImputation(@PathVariable Long id, @PathVariable String type) {
        log.debug("REST request to get new ligne imputation : {}", type);
        LigneImputationTypeDTO ligneImputationTypeDTO = initImputationService.getNewLigneImputationWithTypeAndPeriodeId(id, type);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ligneImputationTypeDTO));
    }
}
