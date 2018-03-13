import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CommentaireComponent } from './commentaire.component';
import { CommentaireDetailComponent } from './commentaire-detail.component';
import { CommentairePopupComponent } from './commentaire-dialog.component';
import { CommentaireDeletePopupComponent } from './commentaire-delete-dialog.component';

export const commentaireRoute: Routes = [
    {
        path: 'commentaire',
        component: CommentaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commentaire/:id',
        component: CommentaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentairePopupRoute: Routes = [
    {
        path: 'commentaire-new',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/edit',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/delete',
        component: CommentaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
