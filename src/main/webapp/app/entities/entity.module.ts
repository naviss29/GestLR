import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GestLrTypeCongeModule } from './type-conge/type-conge.module';
import { GestLrPeriodeModule } from './periode/periode.module';
import { GestLrCommentaireModule } from './commentaire/commentaire.module';
import { GestLrImputationModule } from './imputation/imputation.module';
import { GestLrTypeImputationModule } from './type-imputation/type-imputation.module';
import { GestLrSignatureModule } from './signature/signature.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GestLrTypeCongeModule,
        GestLrPeriodeModule,
        GestLrCommentaireModule,
        GestLrImputationModule,
        GestLrTypeImputationModule,
        GestLrSignatureModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrEntityModule {}
