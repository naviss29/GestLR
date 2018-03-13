package com.lr.myapp.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Periode entity.
 */
public class PeriodeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer annee;

    @NotNull
    private Integer mois;

    private Instant echeance;

    @NotNull
    private String statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Instant getEcheance() {
        return echeance;
    }

    public void setEcheance(Instant echeance) {
        this.echeance = echeance;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeriodeDTO periodeDTO = (PeriodeDTO) o;
        if(periodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodeDTO{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", echeance='" + getEcheance() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
