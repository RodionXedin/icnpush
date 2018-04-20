import { BaseEntity } from './../../shared';

export class Crash implements BaseEntity {
    constructor(
        public id?: number,
        public severity?: number,
        public latitude?: number,
        public longitude?: number,
        public address?: string,
    ) {
    }
}
