import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';
import {
    PeriodeService,
    PeriodePopupService,
    PeriodeComponent,
    PeriodeDetailComponent,
    PeriodeDialogComponent,
    PeriodePopupComponent,
    PeriodeDeletePopupComponent,
    PeriodeDeleteDialogComponent,
    periodeRoute,
    periodePopupRoute,
} from './';

const ENTITY_STATES = [
    ...periodeRoute,
    ...periodePopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PeriodeComponent,
        PeriodeDetailComponent,
        PeriodeDialogComponent,
        PeriodeDeleteDialogComponent,
        PeriodePopupComponent,
        PeriodeDeletePopupComponent,
    ],
    entryComponents: [
        PeriodeComponent,
        PeriodeDialogComponent,
        PeriodePopupComponent,
        PeriodeDeleteDialogComponent,
        PeriodeDeletePopupComponent,
    ],
    providers: [
        PeriodeService,
        PeriodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrPeriodeModule {}
