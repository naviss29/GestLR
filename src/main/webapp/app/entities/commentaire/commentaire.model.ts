import { BaseEntity } from './../../shared';

export class Commentaire implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public auteur?: string,
        public dateSaisie?: any,
        public periodeId?: number
    ) {
    }
}
