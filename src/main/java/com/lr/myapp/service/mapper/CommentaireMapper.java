package com.lr.myapp.service.mapper;

import com.lr.myapp.domain.*;
import com.lr.myapp.service.dto.CommentaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commentaire and its DTO CommentaireDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodeMapper.class})
public interface CommentaireMapper extends EntityMapper<CommentaireDTO, Commentaire> {

    @Mapping(source = "periode.id", target = "periodeId")
    CommentaireDTO toDto(Commentaire commentaire);

    @Mapping(source = "periodeId", target = "periode")
    Commentaire toEntity(CommentaireDTO commentaireDTO);

    default Commentaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commentaire commentaire = new Commentaire();
        commentaire.setId(id);
        return commentaire;
    }
}
