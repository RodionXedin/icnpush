import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IcnpushSharedModule } from '../../shared';
import {
    CompositeService,
    CompositePopupService,
    CompositeComponent,
    CompositeDetailComponent,
    CompositeDialogComponent,
    CompositePopupComponent,
    CompositeDeletePopupComponent,
    CompositeDeleteDialogComponent,
    compositeRoute,
    compositePopupRoute,
} from './';

const ENTITY_STATES = [
    ...compositeRoute,
    ...compositePopupRoute,
];

@NgModule({
    imports: [
        IcnpushSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompositeComponent,
        CompositeDetailComponent,
        CompositeDialogComponent,
        CompositeDeleteDialogComponent,
        CompositePopupComponent,
        CompositeDeletePopupComponent,
    ],
    entryComponents: [
        CompositeComponent,
        CompositeDialogComponent,
        CompositePopupComponent,
        CompositeDeleteDialogComponent,
        CompositeDeletePopupComponent,
    ],
    providers: [
        CompositeService,
        CompositePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushCompositeModule {}
