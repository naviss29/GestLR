import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SignatureComponent } from './signature.component';
import { SignatureDetailComponent } from './signature-detail.component';
import { SignaturePopupComponent } from './signature-dialog.component';
import { SignatureDeletePopupComponent } from './signature-delete-dialog.component';

export const signatureRoute: Routes = [
    {
        path: 'signature',
        component: SignatureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.signature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'signature/:id',
        component: SignatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.signature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const signaturePopupRoute: Routes = [
    {
        path: 'signature-new',
        component: SignaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.signature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'signature/:id/edit',
        component: SignaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.signature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'signature/:id/delete',
        component: SignatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.signature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
