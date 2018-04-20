import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IcnpushSharedModule } from '../../shared';
import {
    ICNPushService,
    ICNPushPopupService,
    ICNPushComponent,
    ICNPushDetailComponent,
    ICNPushDialogComponent,
    ICNPushPopupComponent,
    ICNPushDeletePopupComponent,
    ICNPushDeleteDialogComponent,
    iCNPushRoute,
    iCNPushPopupRoute,
} from './';

const ENTITY_STATES = [
    ...iCNPushRoute,
    ...iCNPushPopupRoute,
];

@NgModule({
    imports: [
        IcnpushSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ICNPushComponent,
        ICNPushDetailComponent,
        ICNPushDialogComponent,
        ICNPushDeleteDialogComponent,
        ICNPushPopupComponent,
        ICNPushDeletePopupComponent,
    ],
    entryComponents: [
        ICNPushComponent,
        ICNPushDialogComponent,
        ICNPushPopupComponent,
        ICNPushDeleteDialogComponent,
        ICNPushDeletePopupComponent,
    ],
    providers: [
        ICNPushService,
        ICNPushPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushICNPushModule {}
