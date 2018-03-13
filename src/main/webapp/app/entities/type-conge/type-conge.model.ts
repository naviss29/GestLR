import { BaseEntity } from './../../shared';

export class TypeConge implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string
    ) {
    }
}
