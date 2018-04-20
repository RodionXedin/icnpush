import { BaseEntity } from './../../shared';

export class Address implements BaseEntity {
    constructor(
        public id?: number,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public state?: string,
        public country?: string,
        public postalCode?: string,
    ) {
    }
}
