import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AccelerationPairComponent } from './acceleration-pair.component';
import { AccelerationPairDetailComponent } from './acceleration-pair-detail.component';
import { AccelerationPairPopupComponent } from './acceleration-pair-dialog.component';
import { AccelerationPairDeletePopupComponent } from './acceleration-pair-delete-dialog.component';

export const accelerationPairRoute: Routes = [
    {
        path: 'acceleration-pair',
        component: AccelerationPairComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.accelerationPair.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'acceleration-pair/:id',
        component: AccelerationPairDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.accelerationPair.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accelerationPairPopupRoute: Routes = [
    {
        path: 'acceleration-pair-new',
        component: AccelerationPairPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.accelerationPair.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acceleration-pair/:id/edit',
        component: AccelerationPairPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.accelerationPair.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'acceleration-pair/:id/delete',
        component: AccelerationPairDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.accelerationPair.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
