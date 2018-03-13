package com.lr.myapp.service.impl;

import com.lr.myapp.service.SignatureService;
import com.lr.myapp.domain.Signature;
import com.lr.myapp.repository.SignatureRepository;
import com.lr.myapp.service.dto.SignatureDTO;
import com.lr.myapp.service.mapper.SignatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Signature.
 */
@Service
@Transactional
public class SignatureServiceImpl implements SignatureService {

    private final Logger log = LoggerFactory.getLogger(SignatureServiceImpl.class);

    private final SignatureRepository signatureRepository;

    private final SignatureMapper signatureMapper;

    public SignatureServiceImpl(SignatureRepository signatureRepository, SignatureMapper signatureMapper) {
        this.signatureRepository = signatureRepository;
        this.signatureMapper = signatureMapper;
    }

    /**
     * Save a signature.
     *
     * @param signatureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SignatureDTO save(SignatureDTO signatureDTO) {
        log.debug("Request to save Signature : {}", signatureDTO);
        Signature signature = signatureMapper.toEntity(signatureDTO);
        signature = signatureRepository.save(signature);
        return signatureMapper.toDto(signature);
    }

    /**
     * Get all the signatures.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SignatureDTO> findAll() {
        log.debug("Request to get all Signatures");
        return signatureRepository.findAll().stream()
            .map(signatureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one signature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SignatureDTO findOne(Long id) {
        log.debug("Request to get Signature : {}", id);
        Signature signature = signatureRepository.findOne(id);
        return signatureMapper.toDto(signature);
    }

    /**
     * Delete the signature by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Signature : {}", id);
        signatureRepository.delete(id);
    }
}
