import { BaseEntity } from './../../shared';

export class SpeedData implements BaseEntity {
    constructor(
        public id?: number,
        public icnId?: number,
        public latitude?: number,
        public longitude?: number,
        public speed?: number,
        public altitude?: number,
        public time?: number,
    ) {
    }
}
