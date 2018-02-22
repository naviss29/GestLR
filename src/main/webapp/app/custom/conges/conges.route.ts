import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CongesComponent } from './conges.component';

export const congesRoute: Route = {
    path: 'conges',
    component: CongesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.custom.conges'
    },
    canActivate: [UserRouteAccessService]
};
