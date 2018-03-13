import { BaseEntity } from './../../shared';

export class Imputation implements BaseEntity {
    constructor(
        public id?: number,
        public client?: string,
        public duree?: number,
        public periodeId?: number,
        public typeId?: number,
    ) {
    }
}
