import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IcnpushSharedModule } from '../../shared';
import {
    CrashService,
    CrashPopupService,
    CrashComponent,
    CrashDetailComponent,
    CrashDialogComponent,
    CrashPopupComponent,
    CrashDeletePopupComponent,
    CrashDeleteDialogComponent,
    crashRoute,
    crashPopupRoute,
} from './';

const ENTITY_STATES = [
    ...crashRoute,
    ...crashPopupRoute,
];

@NgModule({
    imports: [
        IcnpushSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CrashComponent,
        CrashDetailComponent,
        CrashDialogComponent,
        CrashDeleteDialogComponent,
        CrashPopupComponent,
        CrashDeletePopupComponent,
    ],
    entryComponents: [
        CrashComponent,
        CrashDialogComponent,
        CrashPopupComponent,
        CrashDeleteDialogComponent,
        CrashDeletePopupComponent,
    ],
    providers: [
        CrashService,
        CrashPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushCrashModule {}
