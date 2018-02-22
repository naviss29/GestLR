import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';

import {congesRoute} from './conges.route';
import {CongesComponent} from './conges.component';
import {CongesService} from './conges.service';

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild([ congesRoute ])
    ],
    declarations: [
        CongesComponent,
    ],
    entryComponents: [
    ],
    providers: [
        CongesService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrCongesModule {}
