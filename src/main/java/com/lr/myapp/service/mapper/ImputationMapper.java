package com.lr.myapp.service.mapper;

import com.lr.myapp.domain.*;
import com.lr.myapp.service.dto.ImputationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Imputation and its DTO ImputationDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodeMapper.class, TypeImputationMapper.class})
public interface ImputationMapper extends EntityMapper<ImputationDTO, Imputation> {

    @Mapping(source = "periode.id", target = "periodeId")
    @Mapping(source = "type.id", target = "typeId")
    ImputationDTO toDto(Imputation imputation);

    @Mapping(source = "periodeId", target = "periode")
    @Mapping(source = "typeId", target = "type")
    Imputation toEntity(ImputationDTO imputationDTO);

    default Imputation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Imputation imputation = new Imputation();
        imputation.setId(id);
        return imputation;
    }
}
