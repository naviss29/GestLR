package com.lr.myapp.service.impl;

import com.lr.myapp.domain.Periode;
import com.lr.myapp.domain.TypeImputation;
import com.lr.myapp.repository.PeriodeRepository;
import com.lr.myapp.repository.TypeImputationRepository;
import com.lr.myapp.service.InitImputationService;
import com.lr.myapp.service.dto.ColonneCalendrierImputationDTO;
import com.lr.myapp.service.dto.LigneImputationTypeDTO;
import com.lr.myapp.service.dto.SaisieImputationDTO;
import com.lr.myapp.service.dto.TypeImputationDTO;
import com.lr.myapp.service.enumeration.TypeJourEnum;
import com.lr.myapp.service.mapper.PeriodeMapper;
import com.lr.myapp.service.mapper.TypeImputationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Service Implementation for managing Periode.
 */
@Service
@Transactional
public class InitImputationServiceImpl implements InitImputationService {

    private final PeriodeRepository periodeRepository;
    private final TypeImputationRepository typeImputationRepository;

    private final PeriodeMapper periodeMapper;
    private final TypeImputationMapper typeImputationMapper;

    public InitImputationServiceImpl(PeriodeRepository periodeRepository, PeriodeMapper periodeMapper,
                                     TypeImputationRepository typeImputationRepository, TypeImputationMapper typeImputationMapper) {
        this.periodeRepository = periodeRepository;
        this.periodeMapper = periodeMapper;

        this.typeImputationRepository = typeImputationRepository;
        this.typeImputationMapper = typeImputationMapper;
    }

    @Override
    public LigneImputationTypeDTO getNewLigneImputationWithTypeAndPeriodeId(Long periodeId, String type) {
        LigneImputationTypeDTO ligneImputationTypeDTO = new LigneImputationTypeDTO();

        TypeImputation typeImputation = typeImputationRepository.findByCode(type);
        TypeImputationDTO typeImputationDTO = typeImputationMapper.toDto(typeImputation);
        // Type imputation récupéré
        ligneImputationTypeDTO.setTypeImputation(typeImputationDTO);

        Periode periode = periodeRepository.findOne(periodeId);
        Integer annee = periode.getAnnee();
        Integer mois = periode.getMois();
        Integer iDay = 1;

        // Initialisation du calendrier
        Calendar mycal = new GregorianCalendar(annee, mois, iDay);

        // récupération du nombre de jours dans le mois
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<SaisieImputationDTO> saisieImputations = new ArrayList<>();
        for(int i = 1; i <= daysInMonth; i++) {
            SaisieImputationDTO imputation = new SaisieImputationDTO();
            imputation.setJour(i);
            saisieImputations.add(imputation);
        }

        return ligneImputationTypeDTO;
    }

    public List<ColonneCalendrierImputationDTO> getCalendrierImputationsForPeriode(Long periodeId) {

        Periode periode = periodeRepository.findOne(periodeId);
        Integer annee = periode.getAnnee();
        Integer mois = periode.getMois();
        Integer iDay = 1;

        // Initialisation du calendrier
        Calendar mycal = new GregorianCalendar(annee, mois, iDay);

        // récupération du nombre de jours dans le mois
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<ColonneCalendrierImputationDTO> calendrierImputations = new ArrayList<>();
        for(int i = 1; i <= daysInMonth; i++) {
            ColonneCalendrierImputationDTO journee = new ColonneCalendrierImputationDTO();
            journee.setJour(i);
            journee.setLibelleJour(String.valueOf(i));

            Calendar calDay = new GregorianCalendar(annee, mois, iDay);
            TypeJourEnum typeJour = getTypeJourneeFromCalendarDay(calDay);
            journee.setType(typeJour.name());
        }

        return calendrierImputations;
    }

    private TypeJourEnum getTypeJourneeFromCalendarDay(Calendar day) {
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return TypeJourEnum.WE;
        }
        return TypeJourEnum.OUVRE;
    }

}
