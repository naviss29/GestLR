import { Component, OnInit } from '@angular/core';

import { Commentaire } from '../../entities/commentaire/commentaire.model';
import { Imputation } from '../../entities/imputation/imputation.model';
import { CalendrierImputation } from '../models/calendrier-imputations.model';
import { MapImputation } from '../models/map-imputations.model';

@Component({
  selector: 'jhi-saisie-temps',
  templateUrl: './saisie-temps.component.html',
  styleUrls: ['saisie-temps.css']
})
export class SaisieTempsComponent implements OnInit {

    /* Initialisation des variables */
    commentaire: string;
    historiqueCommentaires: Commentaire[];
    calendrierImputations: CalendrierImputation[];
    mapImputations: MapImputation[];
    journees: Imputation[];
    stacked: boolean;

    constructor() {}

    ngOnInit() {

        this.historiqueCommentaires = [
            {id: 1, libelle: 'Exemple de commentaire historisé 1', dateSaisie: 'Date du jour 1', auteur: 'Pascal'},
            {id: 2, libelle: 'Exemple de commentaire historisé 2', dateSaisie: 'Date du jour 2', auteur: 'Alan'}
        ];

        this.calendrierImputations = [
            {libelleJour: '1', jour: 1, type: 'OUVRE'},
            {libelleJour: '2', jour: 2, type: 'OUVRE'},
            {libelleJour: '3', jour: 3, type: 'WE'},
            {libelleJour: '4', jour: 4, type: 'WE'},
            {libelleJour: '5', jour: 5, type: 'OUVRE'},
            {libelleJour: '6', jour: 6, type: 'OUVRE'},
            {libelleJour: '7', jour: 7, type: 'OUVRE'},
            {libelleJour: '8', jour: 8, type: 'OUVRE'},
            {libelleJour: '9', jour: 9, type: 'OUVRE'},
            {libelleJour: '10', jour: 10, type: 'WE'},
            {libelleJour: '11', jour: 11, type: 'WE'},
            {libelleJour: '12', jour: 12, type: 'OUVRE'},
            {libelleJour: '13', jour: 13, type: 'OUVRE'},
            {libelleJour: '14', jour: 14, type: 'OUVRE'},
            {libelleJour: '15', jour: 15, type: 'OUVRE'},
            {libelleJour: '16', jour: 16, type: 'OUVRE'},
            {libelleJour: '17', jour: 17, type: 'WE'},
            {libelleJour: '18', jour: 18, type: 'WE'},
            {libelleJour: '19', jour: 19, type: 'OUVRE'},
            {libelleJour: '20', jour: 20, type: 'OUVRE'},
            {libelleJour: '21', jour: 21, type: 'OUVRE'},
            {libelleJour: '22', jour: 22, type: 'OUVRE'},
            {libelleJour: '23', jour: 23, type: 'OUVRE'},
            {libelleJour: '24', jour: 24, type: 'WE'},
            {libelleJour: '25', jour: 25, type: 'WE'},
            {libelleJour: '26', jour: 26, type: 'OUVRE'},
            {libelleJour: '27', jour: 27, type: 'OUVRE'},
            {libelleJour: '28', jour: 28, type: 'OUVRE'},
            {libelleJour: '29', jour: 29, type: 'OUVRE'},
            {libelleJour: '30', jour: 30, type: 'OUVRE'},
        ];

        this.journees = [
            {id: 1, jour: 1, client: 'Arkea'},
            {id: 2, jour: 2, client: 'Arkea'},
            {id: 3, jour: 3, client: 'Arkea'},
            {id: 4, jour: 4, client: 'Arkea'},
            {id: 5, jour: 5, client: 'Arkea'},
            {id: 6, jour: 6, client: 'Arkea'},
            {id: 7, jour: 7, client: 'Arkea'},
            {id: 8, jour: 8, client: 'Arkea'},
            {id: 9, jour: 9, client: 'Arkea'},
            {id: 10, jour: 10, client: 'Arkea'},
            {id: 11, jour: 11, client: 'Arkea'},
            {id: 12, jour: 12, client: 'Arkea'},
            {id: 13, jour: 13, client: 'Arkea'},
            {id: 14, jour: 14, client: 'Arkea'},
            {id: 15, jour: 15, client: 'Arkea'},
            {id: 16, jour: 16, client: 'Arkea'},
            {id: 17, jour: 17, client: 'Arkea'},
            {id: 18, jour: 18, client: 'Arkea'},
            {id: 19, jour: 19, client: 'Arkea'},
            {id: 20, jour: 20, client: 'Arkea'},
            {id: 21, jour: 21, client: 'Arkea'},
            {id: 22, jour: 22, client: 'Arkea'},
            {id: 23, jour: 23, client: 'Arkea'},
            {id: 24, jour: 24, client: 'Arkea'},
            {id: 25, jour: 25, client: 'Arkea'},
            {id: 26, jour: 26, client: 'Arkea'},
            {id: 27, jour: 27, client: 'Arkea'},
            {id: 28, jour: 28, client: 'Arkea'},
            {id: 29, jour: 29, client: 'Arkea'},
            {id: 30, jour: 30, client: 'Arkea'}
        ];

        const nouvelleLigne = {type: 'MISSION', imputations: this.journees};
        this.mapImputations = [nouvelleLigne];
    }

    addLigne(): void {
        const nouvelleLigne = {type: 'TST', imputations: this.journees};
        this.mapImputations = [...this.mapImputations, nouvelleLigne];
    }

    isJourWeOuFerie(imputation: Imputation): boolean {
        return this.calendrierImputations[imputation.jour - 1].type === 'WE' || this.calendrierImputations[imputation.jour - 1].type === 'FERIE';
    }

    sommeImputationsByType(imputations: Imputation[]): number {
        let somme: number;
        for (const imputation of imputations) {
            if (imputation.duree !== undefined) {
                if (somme === undefined) {
                    somme = 0;
                }
                somme = this.addition(somme, imputation.duree);
            }
        }
        return somme;
    }

    sommeTotaleImputationsByType(): number {
        let sommeTotale: number;
        for (const imp of this.mapImputations) {
            if (sommeTotale === undefined) {
                sommeTotale = 0;
            }
            let sousTotal = this.sommeImputationsByType(imp.imputations);

            if (sousTotal === undefined) {
                sousTotal = 0;
            }
            sommeTotale = this.addition(sommeTotale, sousTotal);
        }
        return sommeTotale;
    }

    addition(num1: number, num2: number): number {
        return (num1 + num2);
    }

    checkDureeSaisie(duree: number): void {
        if (duree !== undefined) {
            if (duree < 0) {
                duree = 0;
            } else if (duree > 1) {
                duree = 1;
            }
        }
    }
}
