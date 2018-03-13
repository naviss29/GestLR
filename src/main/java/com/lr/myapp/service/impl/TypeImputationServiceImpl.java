package com.lr.myapp.service.impl;

import com.lr.myapp.service.TypeImputationService;
import com.lr.myapp.domain.TypeImputation;
import com.lr.myapp.repository.TypeImputationRepository;
import com.lr.myapp.service.dto.TypeImputationDTO;
import com.lr.myapp.service.mapper.TypeImputationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TypeImputation.
 */
@Service
@Transactional
public class TypeImputationServiceImpl implements TypeImputationService {

    private final Logger log = LoggerFactory.getLogger(TypeImputationServiceImpl.class);

    private final TypeImputationRepository typeImputationRepository;

    private final TypeImputationMapper typeImputationMapper;

    public TypeImputationServiceImpl(TypeImputationRepository typeImputationRepository, TypeImputationMapper typeImputationMapper) {
        this.typeImputationRepository = typeImputationRepository;
        this.typeImputationMapper = typeImputationMapper;
    }

    /**
     * Save a typeImputation.
     *
     * @param typeImputationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeImputationDTO save(TypeImputationDTO typeImputationDTO) {
        log.debug("Request to save TypeImputation : {}", typeImputationDTO);
        TypeImputation typeImputation = typeImputationMapper.toEntity(typeImputationDTO);
        typeImputation = typeImputationRepository.save(typeImputation);
        return typeImputationMapper.toDto(typeImputation);
    }

    /**
     * Get all the typeImputations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TypeImputationDTO> findAll() {
        log.debug("Request to get all TypeImputations");
        return typeImputationRepository.findAll().stream()
            .map(typeImputationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one typeImputation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeImputationDTO findOne(Long id) {
        log.debug("Request to get TypeImputation : {}", id);
        TypeImputation typeImputation = typeImputationRepository.findOne(id);
        return typeImputationMapper.toDto(typeImputation);
    }

    /**
     * Delete the typeImputation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeImputation : {}", id);
        typeImputationRepository.delete(id);
    }
}
