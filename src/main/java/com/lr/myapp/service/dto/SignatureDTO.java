package com.lr.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Signature entity.
 */
public class SignatureDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomSignataire;

    @NotNull
    private String prenomSignataire;

    @NotNull
    private String emailSignataire;

    @NotNull
    private String statut;

    private Long periodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSignataire() {
        return nomSignataire;
    }

    public void setNomSignataire(String nomSignataire) {
        this.nomSignataire = nomSignataire;
    }

    public String getPrenomSignataire() {
        return prenomSignataire;
    }

    public void setPrenomSignataire(String prenomSignataire) {
        this.prenomSignataire = prenomSignataire;
    }

    public String getEmailSignataire() {
        return emailSignataire;
    }

    public void setEmailSignataire(String emailSignataire) {
        this.emailSignataire = emailSignataire;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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

        SignatureDTO signatureDTO = (SignatureDTO) o;
        if(signatureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signatureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignatureDTO{" +
            "id=" + getId() +
            ", nomSignataire='" + getNomSignataire() + "'" +
            ", prenomSignataire='" + getPrenomSignataire() + "'" +
            ", emailSignataire='" + getEmailSignataire() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
