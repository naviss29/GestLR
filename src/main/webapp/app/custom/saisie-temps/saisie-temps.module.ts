import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';

import {saisieTempsRoute} from './saisie-temps.route';
import {SaisieTempsComponent} from './saisie-temps.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AccordionModule, RatingModule, CalendarModule, ButtonModule } from 'primeng/primeng';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {DataTableModule} from 'primeng/datatable';

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild([ saisieTempsRoute ]),
        BrowserAnimationsModule,
        AccordionModule,
        RatingModule,
        CalendarModule,
        ButtonModule,
        InputTextareaModule,
        DataTableModule
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
