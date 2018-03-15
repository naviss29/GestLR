import { Imputation } from '../../entities/imputation/imputation.model';

export class MapImputation {
    constructor(
        public type?: any,
        public imputations?: Imputation[]
    ) {
    }
}
