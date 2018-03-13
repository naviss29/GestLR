import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ImputationComponent } from './imputation.component';
import { ImputationDetailComponent } from './imputation-detail.component';
import { ImputationPopupComponent } from './imputation-dialog.component';
import { ImputationDeletePopupComponent } from './imputation-delete-dialog.component';

export const imputationRoute: Routes = [
    {
        path: 'imputation',
        component: ImputationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.imputation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'imputation/:id',
        component: ImputationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.imputation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imputationPopupRoute: Routes = [
    {
        path: 'imputation-new',
        component: ImputationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.imputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'imputation/:id/edit',
        component: ImputationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.imputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'imputation/:id/delete',
        component: ImputationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.imputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
