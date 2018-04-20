import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IcnpushSharedModule } from '../../shared';
import {
    SpeedDataService,
    SpeedDataPopupService,
    SpeedDataComponent,
    SpeedDataDetailComponent,
    SpeedDataDialogComponent,
    SpeedDataPopupComponent,
    SpeedDataDeletePopupComponent,
    SpeedDataDeleteDialogComponent,
    speedDataRoute,
    speedDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...speedDataRoute,
    ...speedDataPopupRoute,
];

@NgModule({
    imports: [
        IcnpushSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SpeedDataComponent,
        SpeedDataDetailComponent,
        SpeedDataDialogComponent,
        SpeedDataDeleteDialogComponent,
        SpeedDataPopupComponent,
        SpeedDataDeletePopupComponent,
    ],
    entryComponents: [
        SpeedDataComponent,
        SpeedDataDialogComponent,
        SpeedDataPopupComponent,
        SpeedDataDeleteDialogComponent,
        SpeedDataDeletePopupComponent,
    ],
    providers: [
        SpeedDataService,
        SpeedDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushSpeedDataModule {}
