import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TypeCongeComponent } from './type-conge.component';
import { TypeCongeDetailComponent } from './type-conge-detail.component';
import { TypeCongePopupComponent } from './type-conge-dialog.component';
import { TypeCongeDeletePopupComponent } from './type-conge-delete-dialog.component';

export const typeCongeRoute: Routes = [
    {
        path: 'type-conge',
        component: TypeCongeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeConge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-conge/:id',
        component: TypeCongeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeConge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeCongePopupRoute: Routes = [
    {
        path: 'type-conge-new',
        component: TypeCongePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-conge/:id/edit',
        component: TypeCongePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-conge/:id/delete',
        component: TypeCongeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.typeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
