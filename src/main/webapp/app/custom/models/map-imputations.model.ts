import { Imputation } from '../../entities/imputation/imputation.model';

export class MapImputation {
    constructor(
        public type?: string,
        public imputations?: Imputation[]
    ) {
    }
}
