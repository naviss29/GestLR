import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../shared';

import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { CardModule } from 'primeng/card';

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild([ HOME_ROUTE ]),
        CardModule
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrHomeModule {}
