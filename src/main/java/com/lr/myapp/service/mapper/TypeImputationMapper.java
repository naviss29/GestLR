package com.lr.myapp.service.mapper;

import com.lr.myapp.domain.*;
import com.lr.myapp.service.dto.TypeImputationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeImputation and its DTO TypeImputationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeImputationMapper extends EntityMapper<TypeImputationDTO, TypeImputation> {



    default TypeImputation fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeImputation typeImputation = new TypeImputation();
        typeImputation.setId(id);
        return typeImputation;
    }
}
