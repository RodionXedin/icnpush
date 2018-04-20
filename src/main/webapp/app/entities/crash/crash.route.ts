import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CrashComponent } from './crash.component';
import { CrashDetailComponent } from './crash-detail.component';
import { CrashPopupComponent } from './crash-dialog.component';
import { CrashDeletePopupComponent } from './crash-delete-dialog.component';

export const crashRoute: Routes = [
    {
        path: 'crash',
        component: CrashComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.crash.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'crash/:id',
        component: CrashDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.crash.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const crashPopupRoute: Routes = [
    {
        path: 'crash-new',
        component: CrashPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.crash.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'crash/:id/edit',
        component: CrashPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.crash.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'crash/:id/delete',
        component: CrashDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.crash.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
