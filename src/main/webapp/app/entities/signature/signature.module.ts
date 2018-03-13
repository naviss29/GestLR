import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestLrSharedModule } from '../../shared';
import {
    SignatureService,
    SignaturePopupService,
    SignatureComponent,
    SignatureDetailComponent,
    SignatureDialogComponent,
    SignaturePopupComponent,
    SignatureDeletePopupComponent,
    SignatureDeleteDialogComponent,
    signatureRoute,
    signaturePopupRoute,
} from './';

const ENTITY_STATES = [
    ...signatureRoute,
    ...signaturePopupRoute,
];

@NgModule({
    imports: [
        GestLrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SignatureComponent,
        SignatureDetailComponent,
        SignatureDialogComponent,
        SignatureDeleteDialogComponent,
        SignaturePopupComponent,
        SignatureDeletePopupComponent,
    ],
    entryComponents: [
        SignatureComponent,
        SignatureDialogComponent,
        SignaturePopupComponent,
        SignatureDeleteDialogComponent,
        SignatureDeletePopupComponent,
    ],
    providers: [
        SignatureService,
        SignaturePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestLrSignatureModule {}
