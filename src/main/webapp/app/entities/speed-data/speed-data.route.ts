import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SpeedDataComponent } from './speed-data.component';
import { SpeedDataDetailComponent } from './speed-data-detail.component';
import { SpeedDataPopupComponent } from './speed-data-dialog.component';
import { SpeedDataDeletePopupComponent } from './speed-data-delete-dialog.component';

export const speedDataRoute: Routes = [
    {
        path: 'speed-data',
        component: SpeedDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.speedData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'speed-data/:id',
        component: SpeedDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.speedData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const speedDataPopupRoute: Routes = [
    {
        path: 'speed-data-new',
        component: SpeedDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.speedData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'speed-data/:id/edit',
        component: SpeedDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.speedData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'speed-data/:id/delete',
        component: SpeedDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.speedData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
