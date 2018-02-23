import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SaisieTempsComponent } from './saisie-temps.component';

export const saisieTempsRoute: Route = {
    path: 'saisie-temps',
    component: SaisieTempsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.custom.saisie-temps'
    },
    canActivate: [UserRouteAccessService]
};
