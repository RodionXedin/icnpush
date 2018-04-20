import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IcnpushSharedModule } from '../../shared';
import {
    AccelerationPairService,
    AccelerationPairPopupService,
    AccelerationPairComponent,
    AccelerationPairDetailComponent,
    AccelerationPairDialogComponent,
    AccelerationPairPopupComponent,
    AccelerationPairDeletePopupComponent,
    AccelerationPairDeleteDialogComponent,
    accelerationPairRoute,
    accelerationPairPopupRoute,
} from './';

const ENTITY_STATES = [
    ...accelerationPairRoute,
    ...accelerationPairPopupRoute,
];

@NgModule({
    imports: [
        IcnpushSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AccelerationPairComponent,
        AccelerationPairDetailComponent,
        AccelerationPairDialogComponent,
        AccelerationPairDeleteDialogComponent,
        AccelerationPairPopupComponent,
        AccelerationPairDeletePopupComponent,
    ],
    entryComponents: [
        AccelerationPairComponent,
        AccelerationPairDialogComponent,
        AccelerationPairPopupComponent,
        AccelerationPairDeleteDialogComponent,
        AccelerationPairDeletePopupComponent,
    ],
    providers: [
        AccelerationPairService,
        AccelerationPairPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushAccelerationPairModule {}
