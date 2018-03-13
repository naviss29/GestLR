package com.lr.myapp.service.mapper;

import com.lr.myapp.domain.*;
import com.lr.myapp.service.dto.PeriodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Periode and its DTO PeriodeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodeMapper extends EntityMapper<PeriodeDTO, Periode> {


    @Mapping(target = "imputations", ignore = true)
    @Mapping(target = "commentaires", ignore = true)
    @Mapping(target = "signatures", ignore = true)
    Periode toEntity(PeriodeDTO periodeDTO);

    default Periode fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periode periode = new Periode();
        periode.setId(id);
        return periode;
    }
}
