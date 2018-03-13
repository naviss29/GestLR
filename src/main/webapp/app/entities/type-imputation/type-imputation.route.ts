import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TypeImputationComponent } from './type-imputation.component';
import { TypeImputationDetailComponent } from './type-imputation-detail.component';
import { TypeImputationPopupComponent } from './type-imputation-dialog.component';
import { TypeImputationDeletePopupComponent } from './type-imputation-delete-dialog.component';

export const typeImputationRoute: Routes = [
    {
        path: 'type-imputation',
        component: TypeImputationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeImputation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-imputation/:id',
        component: TypeImputationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeImputation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeImputationPopupRoute: Routes = [
    {
        path: 'type-imputation-new',
        component: TypeImputationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeImputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-imputation/:id/edit',
        component: TypeImputationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeImputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-imputation/:id/delete',
        component: TypeImputationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeImputation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
