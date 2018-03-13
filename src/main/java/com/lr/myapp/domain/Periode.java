package com.lr.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Periode.
 */
@Entity
@Table(name = "periode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Periode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "annee", nullable = false)
    private Integer annee;

    @NotNull
    @Column(name = "mois", nullable = false)
    private Integer mois;

    @Column(name = "echeance")
    private Instant echeance;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @OneToMany(mappedBy = "periode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Imputation> imputations = new HashSet<>();

    @OneToMany(mappedBy = "periode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commentaire> commentaires = new HashSet<>();

    @OneToMany(mappedBy = "periode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Signature> signatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public Periode annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return mois;
    }

    public Periode mois(Integer mois) {
        this.mois = mois;
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Instant getEcheance() {
        return echeance;
    }

    public Periode echeance(Instant echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(Instant echeance) {
        this.echeance = echeance;
    }

    public String getStatut() {
        return statut;
    }

    public Periode statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Set<Imputation> getImputations() {
        return imputations;
    }

    public Periode imputations(Set<Imputation> imputations) {
        this.imputations = imputations;
        return this;
    }

    public Periode addImputation(Imputation imputation) {
        this.imputations.add(imputation);
        imputation.setPeriode(this);
        return this;
    }

    public Periode removeImputation(Imputation imputation) {
        this.imputations.remove(imputation);
        imputation.setPeriode(null);
        return this;
    }

    public void setImputations(Set<Imputation> imputations) {
        this.imputations = imputations;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public Periode commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public Periode addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setPeriode(this);
        return this;
    }

    public Periode removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setPeriode(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Set<Signature> getSignatures() {
        return signatures;
    }

    public Periode signatures(Set<Signature> signatures) {
        this.signatures = signatures;
        return this;
    }

    public Periode addSignature(Signature signature) {
        this.signatures.add(signature);
        signature.setPeriode(this);
        return this;
    }

    public Periode removeSignature(Signature signature) {
        this.signatures.remove(signature);
        signature.setPeriode(null);
        return this;
    }

    public void setSignatures(Set<Signature> signatures) {
        this.signatures = signatures;
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
        Periode periode = (Periode) o;
        if (periode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Periode{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", echeance='" + getEcheance() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
