import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IcnpushAccelerationPairModule } from './acceleration-pair/acceleration-pair.module';
import { IcnpushAddressModule } from './address/address.module';
import { IcnpushCompositeModule } from './composite/composite.module';
import { IcnpushCrashModule } from './crash/crash.module';
import { IcnpushICNPushModule } from './icn-push/icn-push.module';
import { IcnpushSpeedDataModule } from './speed-data/speed-data.module';
import { IcnpushVehicleModule } from './vehicle/vehicle.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        IcnpushAccelerationPairModule,
        IcnpushAddressModule,
        IcnpushCompositeModule,
        IcnpushCrashModule,
        IcnpushICNPushModule,
        IcnpushSpeedDataModule,
        IcnpushVehicleModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IcnpushEntityModule {}
