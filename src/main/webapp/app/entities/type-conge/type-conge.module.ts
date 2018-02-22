import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';
import {
    TypeCongeService,
    TypeCongePopupService,
    TypeCongeComponent,
    TypeCongeDetailComponent,
    TypeCongeDialogComponent,
    TypeCongePopupComponent,
    TypeCongeDeletePopupComponent,
    TypeCongeDeleteDialogComponent,
    typeCongeRoute,
    typeCongePopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeCongeRoute,
    ...typeCongePopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeCongeComponent,
        TypeCongeDetailComponent,
        TypeCongeDialogComponent,
        TypeCongeDeleteDialogComponent,
        TypeCongePopupComponent,
        TypeCongeDeletePopupComponent,
    ],
    entryComponents: [
        TypeCongeComponent,
        TypeCongeDialogComponent,
        TypeCongePopupComponent,
        TypeCongeDeleteDialogComponent,
        TypeCongeDeletePopupComponent,
    ],
    providers: [
        TypeCongeService,
        TypeCongePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrTypeCongeModule {}
