package com.lr.myapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Commentaire entity.
 */
public class CommentaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private String auteur;

    @NotNull
    private Instant dateSaisie;

    private Long periodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Instant getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(Instant dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public Long getPeriodeId() {
        return periodeId;
    }

    public void setPeriodeId(Long periodeId) {
        this.periodeId = periodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentaireDTO commentaireDTO = (CommentaireDTO) o;
        if(commentaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentaireDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", auteur='" + getAuteur() + "'" +
            ", dateSaisie='" + getDateSaisie() + "'" +
            "}";
    }
}
