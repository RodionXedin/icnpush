import { BaseEntity } from './../../shared';

export class Vehicle implements BaseEntity {
    constructor(
        public id?: number,
        public vin?: string,
        public vinRegion?: string,
        public make?: string,
        public model?: string,
        public year?: number,
    ) {
    }
}
