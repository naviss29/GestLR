import { BaseEntity } from './../../shared';

export class TypeImputation implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string
    ) {
    }
}
