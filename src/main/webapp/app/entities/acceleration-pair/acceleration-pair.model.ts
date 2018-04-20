import { BaseEntity } from './../../shared';

export class AccelerationPair implements BaseEntity {
    constructor(
        public id?: number,
        public icnId?: number,
        public accX?: number,
        public accY?: number,
        public accZ?: number,
        public time?: number,
    ) {
    }
}
