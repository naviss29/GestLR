import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';

import {saisieTempsRoute} from './saisie-temps.route';
import {SaisieTempsComponent} from './saisie-temps.component';

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild([ saisieTempsRoute ])
    ],
    declarations: [
        SaisieTempsComponent,
    ],
    entryComponents: [
    ],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrSaisieTempsModule {}
