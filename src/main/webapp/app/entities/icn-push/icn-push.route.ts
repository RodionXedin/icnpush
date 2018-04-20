import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ICNPushComponent } from './icn-push.component';
import { ICNPushDetailComponent } from './icn-push-detail.component';
import { ICNPushPopupComponent } from './icn-push-dialog.component';
import { ICNPushDeletePopupComponent } from './icn-push-delete-dialog.component';

export const iCNPushRoute: Routes = [
    {
        path: 'icn-push',
        component: ICNPushComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.iCNPush.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'icn-push/:id',
        component: ICNPushDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.iCNPush.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iCNPushPopupRoute: Routes = [
    {
        path: 'icn-push-new',
        component: ICNPushPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.iCNPush.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'icn-push/:id/edit',
        component: ICNPushPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.iCNPush.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'icn-push/:id/delete',
        component: ICNPushDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'icnpushApp.iCNPush.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
