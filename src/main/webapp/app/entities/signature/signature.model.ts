import { BaseEntity } from './../../shared';

export class Signature implements BaseEntity {
    constructor(
        public id?: number,
        public nomSignataire?: string,
        public prenomSignataire?: string,
        public emailSignataire?: string,
        public statut?: string,
        public periodeId?: number,
    ) {
    }
}
