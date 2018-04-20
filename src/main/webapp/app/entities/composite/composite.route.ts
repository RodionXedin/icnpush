import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompositeComponent } from './composite.component';
import { CompositeDetailComponent } from './composite-detail.component';
import { CompositePopupComponent } from './composite-dialog.component';
import { CompositeDeletePopupComponent } from './composite-delete-dialog.component';

export const compositeRoute: Routes = [
    {
        path: 'composite',
        component: CompositeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.composite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'composite/:id',
        component: CompositeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.composite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compositePopupRoute: Routes = [
    {
        path: 'composite-new',
        component: CompositePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.composite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'composite/:id/edit',
        component: CompositePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.composite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'composite/:id/delete',
        component: CompositeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.composite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
