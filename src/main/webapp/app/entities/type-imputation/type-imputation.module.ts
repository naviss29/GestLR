import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';
import {
    TypeImputationService,
    TypeImputationPopupService,
    TypeImputationComponent,
    TypeImputationDetailComponent,
    TypeImputationDialogComponent,
    TypeImputationPopupComponent,
    TypeImputationDeletePopupComponent,
    TypeImputationDeleteDialogComponent,
    typeImputationRoute,
    typeImputationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeImputationRoute,
    ...typeImputationPopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeImputationComponent,
        TypeImputationDetailComponent,
        TypeImputationDialogComponent,
        TypeImputationDeleteDialogComponent,
        TypeImputationPopupComponent,
        TypeImputationDeletePopupComponent,
    ],
    entryComponents: [
        TypeImputationComponent,
        TypeImputationDialogComponent,
        TypeImputationPopupComponent,
        TypeImputationDeleteDialogComponent,
        TypeImputationDeletePopupComponent,
    ],
    providers: [
        TypeImputationService,
        TypeImputationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrTypeImputationModule {}
