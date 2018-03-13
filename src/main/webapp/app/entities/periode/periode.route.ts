import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PeriodeComponent } from './periode.component';
import { PeriodeDetailComponent } from './periode-detail.component';
import { PeriodePopupComponent } from './periode-dialog.component';
import { PeriodeDeletePopupComponent } from './periode-delete-dialog.component';

export const periodeRoute: Routes = [
    {
        path: 'periode',
        component: PeriodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.periode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'periode/:id',
        component: PeriodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.periode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodePopupRoute: Routes = [
    {
        path: 'periode-new',
        component: PeriodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.periode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'periode/:id/edit',
        component: PeriodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.periode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'periode/:id/delete',
        component: PeriodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.periode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
