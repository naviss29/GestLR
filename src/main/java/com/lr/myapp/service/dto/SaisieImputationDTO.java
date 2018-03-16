package com.lr.myapp.service.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class SaisieImputationDTO implements Serializable {

    @NotNull
    private Integer jour;

    @NotNull
    private String client;

    private Float duree;

    public Integer getJour() {
        return jour;
    }

    public void setJour(Integer jour) {
        this.jour = jour;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaisieImputationDTO that = (SaisieImputationDTO) o;

        if (jour != null ? !jour.equals(that.jour) : that.jour != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return duree != null ? duree.equals(that.duree) : that.duree == null;

    }

    @Override
    public int hashCode() {
        int result = jour != null ? jour.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (duree != null ? duree.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SaisieImputationDTO{" +
            "jour=" + jour +
            ", client='" + client + '\'' +
            ", duree=" + duree +
            '}';
    }
}
