package com.lr.myapp.service.dto;

import java.util.List;

public class LigneImputationTypeDTO {

    private TypeImputationDTO typeImputation;
    private List<SaisieImputationDTO> imputations;

    public TypeImputationDTO getTypeImputation() {
        return typeImputation;
    }

    public void setTypeImputation(TypeImputationDTO typeImputation) {
        this.typeImputation = typeImputation;
    }

    public List<SaisieImputationDTO> getImputations() {
        return imputations;
    }

    public void setImputations(List<SaisieImputationDTO> imputations) {
        this.imputations = imputations;
    }
}
