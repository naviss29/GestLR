package com.lr.myapp.service.mapper;

import com.lr.myapp.domain.*;
import com.lr.myapp.service.dto.SignatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Signature and its DTO SignatureDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodeMapper.class})
public interface SignatureMapper extends EntityMapper<SignatureDTO, Signature> {

    @Mapping(source = "periode.id", target = "periodeId")
    SignatureDTO toDto(Signature signature);

    @Mapping(source = "periodeId", target = "periode")
    Signature toEntity(SignatureDTO signatureDTO);

    default Signature fromId(Long id) {
        if (id == null) {
            return null;
        }
        Signature signature = new Signature();
        signature.setId(id);
        return signature;
    }
}
