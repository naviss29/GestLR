package com.lr.myapp.service.impl;

import com.lr.myapp.service.PeriodeService;
import com.lr.myapp.domain.Periode;
import com.lr.myapp.repository.PeriodeRepository;
import com.lr.myapp.service.dto.PeriodeDTO;
import com.lr.myapp.service.mapper.PeriodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Periode.
 */
@Service
@Transactional
public class PeriodeServiceImpl implements PeriodeService {

    private final Logger log = LoggerFactory.getLogger(PeriodeServiceImpl.class);

    private final PeriodeRepository periodeRepository;

    private final PeriodeMapper periodeMapper;

    public PeriodeServiceImpl(PeriodeRepository periodeRepository, PeriodeMapper periodeMapper) {
        this.periodeRepository = periodeRepository;
        this.periodeMapper = periodeMapper;
    }

    /**
     * Save a periode.
     *
     * @param periodeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeriodeDTO save(PeriodeDTO periodeDTO) {
        log.debug("Request to save Periode : {}", periodeDTO);
        Periode periode = periodeMapper.toEntity(periodeDTO);
        periode = periodeRepository.save(periode);
        return periodeMapper.toDto(periode);
    }

    /**
     * Get all the periodes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeriodeDTO> findAll() {
        log.debug("Request to get all Periodes");
        return periodeRepository.findAll().stream()
            .map(periodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one periode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PeriodeDTO findOne(Long id) {
        log.debug("Request to get Periode : {}", id);
        Periode periode = periodeRepository.findOne(id);
        return periodeMapper.toDto(periode);
    }

    /**
     * Delete the periode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Periode : {}", id);
        periodeRepository.delete(id);
    }
}
