package com.lr.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Imputation entity.
 */
public class ImputationDTO implements Serializable {

    private Long id;

    @NotNull
    private String client;

    private Float duree;

    private Long periodeId;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public Long getPeriodeId() {
        return periodeId;
    }

    public void setPeriodeId(Long periodeId) {
        this.periodeId = periodeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeImputationId) {
        this.typeId = typeImputationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImputationDTO imputationDTO = (ImputationDTO) o;
        if(imputationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imputationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImputationDTO{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", duree=" + getDuree() +
            "}";
    }
}
