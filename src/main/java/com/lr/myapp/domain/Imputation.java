package com.lr.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Imputation.
 */
@Entity
@Table(name = "imputation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imputation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "duree")
    private Float duree;

    @ManyToOne
    private Periode periode;

    @OneToOne
    @JoinColumn(unique = true)
    private TypeImputation type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public Imputation client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Float getDuree() {
        return duree;
    }

    public Imputation duree(Float duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public Periode getPeriode() {
        return periode;
    }

    public Imputation periode(Periode periode) {
        this.periode = periode;
        return this;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public TypeImputation getType() {
        return type;
    }

    public Imputation type(TypeImputation typeImputation) {
        this.type = typeImputation;
        return this;
    }

    public void setType(TypeImputation typeImputation) {
        this.type = typeImputation;
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
        Imputation imputation = (Imputation) o;
        if (imputation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imputation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Imputation{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", duree=" + getDuree() +
            "}";
    }
}
