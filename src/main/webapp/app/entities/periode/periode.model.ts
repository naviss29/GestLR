import { BaseEntity } from './../../shared';

export class Periode implements BaseEntity {
    constructor(
        public id?: number,
        public annee?: number,
        public mois?: number,
        public echeance?: any,
        public statut?: string,
        public imputations?: BaseEntity[],
        public commentaires?: BaseEntity[],
        public signatures?: BaseEntity[],
    ) {
    }
}
