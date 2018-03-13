import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';
import {
    ImputationService,
    ImputationPopupService,
    ImputationComponent,
    ImputationDetailComponent,
    ImputationDialogComponent,
    ImputationPopupComponent,
    ImputationDeletePopupComponent,
    ImputationDeleteDialogComponent,
    imputationRoute,
    imputationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...imputationRoute,
    ...imputationPopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ImputationComponent,
        ImputationDetailComponent,
        ImputationDialogComponent,
        ImputationDeleteDialogComponent,
        ImputationPopupComponent,
        ImputationDeletePopupComponent,
    ],
    entryComponents: [
        ImputationComponent,
        ImputationDialogComponent,
        ImputationPopupComponent,
        ImputationDeleteDialogComponent,
        ImputationDeletePopupComponent,
    ],
    providers: [
        ImputationService,
        ImputationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrImputationModule {}
