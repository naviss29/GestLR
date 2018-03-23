import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';

import { saisieTempsRoute, saisieTempsPopupRoute } from './saisie-temps.route';
import { SaisieTempsComponent } from './saisie-temps.component';
import { SaisieTempsDialogComponent, SaisieTempsPopupComponent } from './saisie-temps-dialog.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/primeng';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TableModule } from 'primeng/table';

const ENTITY_STATES = [
    ...saisieTempsRoute,
    ...saisieTempsPopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        BrowserAnimationsModule,
        ButtonModule,
        InputTextareaModule,
        TableModule
    ],
    declarations: [
        SaisieTempsComponent,
        SaisieTempsDialogComponent,
        SaisieTempsPopupComponent
    ],
    entryComponents: [
        SaisieTempsDialogComponent,
        SaisieTempsPopupComponent
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrSaisieTempsModule {}
