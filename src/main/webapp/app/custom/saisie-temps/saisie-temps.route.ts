import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SaisieTempsComponent } from './saisie-temps.component';
import { SaisieTempsPopupComponent } from './saisie-temps-dialog.component';

export const saisieTempsRoute: Routes = [
    {
        path: 'saisie-temps',
        component: SaisieTempsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'global.menu.custom.saisie-temps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saisieTempsPopupRoute: Routes = [
    {
        path: 'saisie-temps-new',
        component: SaisieTempsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestLrApp.saisieTemps.dialog.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
