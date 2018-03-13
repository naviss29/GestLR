package com.lr.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Signature.
 */
@Entity
@Table(name = "signature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Signature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_signataire", nullable = false)
    private String nomSignataire;

    @NotNull
    @Column(name = "prenom_signataire", nullable = false)
    private String prenomSignataire;

    @NotNull
    @Column(name = "email_signataire", nullable = false)
    private String emailSignataire;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @ManyToOne
    private Periode periode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSignataire() {
        return nomSignataire;
    }

    public Signature nomSignataire(String nomSignataire) {
        this.nomSignataire = nomSignataire;
        return this;
    }

    public void setNomSignataire(String nomSignataire) {
        this.nomSignataire = nomSignataire;
    }

    public String getPrenomSignataire() {
        return prenomSignataire;
    }

    public Signature prenomSignataire(String prenomSignataire) {
        this.prenomSignataire = prenomSignataire;
        return this;
    }

    public void setPrenomSignataire(String prenomSignataire) {
        this.prenomSignataire = prenomSignataire;
    }

    public String getEmailSignataire() {
        return emailSignataire;
    }

    public Signature emailSignataire(String emailSignataire) {
        this.emailSignataire = emailSignataire;
        return this;
    }

    public void setEmailSignataire(String emailSignataire) {
        this.emailSignataire = emailSignataire;
    }

    public String getStatut() {
        return statut;
    }

    public Signature statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Periode getPeriode() {
        return periode;
    }

    public Signature periode(Periode periode) {
        this.periode = periode;
        return this;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Signature signature = (Signature) o;
        if (signature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Signature{" +
            "id=" + getId() +
            ", nomSignataire='" + getNomSignataire() + "'" +
            ", prenomSignataire='" + getPrenomSignataire() + "'" +
            ", emailSignataire='" + getEmailSignataire() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
