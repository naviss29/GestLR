package com.lr.myapp.service.impl;

import com.lr.myapp.service.ImputationService;
import com.lr.myapp.domain.Imputation;
import com.lr.myapp.repository.ImputationRepository;
import com.lr.myapp.service.dto.ImputationDTO;
import com.lr.myapp.service.mapper.ImputationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Imputation.
 */
@Service
@Transactional
public class ImputationServiceImpl implements ImputationService {

    private final Logger log = LoggerFactory.getLogger(ImputationServiceImpl.class);

    private final ImputationRepository imputationRepository;

    private final ImputationMapper imputationMapper;

    public ImputationServiceImpl(ImputationRepository imputationRepository, ImputationMapper imputationMapper) {
        this.imputationRepository = imputationRepository;
        this.imputationMapper = imputationMapper;
    }

    /**
     * Save a imputation.
     *
     * @param imputationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImputationDTO save(ImputationDTO imputationDTO) {
        log.debug("Request to save Imputation : {}", imputationDTO);
        Imputation imputation = imputationMapper.toEntity(imputationDTO);
        imputation = imputationRepository.save(imputation);
        return imputationMapper.toDto(imputation);
    }

    /**
     * Get all the imputations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImputationDTO> findAll() {
        log.debug("Request to get all Imputations");
        return imputationRepository.findAll().stream()
            .map(imputationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one imputation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ImputationDTO findOne(Long id) {
        log.debug("Request to get Imputation : {}", id);
        Imputation imputation = imputationRepository.findOne(id);
        return imputationMapper.toDto(imputation);
    }

    /**
     * Delete the imputation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imputation : {}", id);
        imputationRepository.delete(id);
    }
}
