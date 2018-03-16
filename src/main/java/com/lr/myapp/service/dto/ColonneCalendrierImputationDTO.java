package com.lr.myapp.service.dto;

public class ColonneCalendrierImputationDTO {

    private String libelleJour;
    private Integer jour;
    private String type;

    public String getLibelleJour() {
        return libelleJour;
    }

    public void setLibelleJour(String libelleJour) {
        this.libelleJour = libelleJour;
    }

    public Integer getJour() {
        return jour;
    }

    public void setJour(Integer jour) {
        this.jour = jour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
