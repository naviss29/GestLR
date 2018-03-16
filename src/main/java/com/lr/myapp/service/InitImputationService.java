package com.lr.myapp.service;

import com.lr.myapp.service.dto.LigneImputationTypeDTO;

public interface InitImputationService {

    LigneImputationTypeDTO getNewLigneImputationWithTypeAndPeriodeId(Long periodeId, String type);

}
