import { BaseEntity } from './../../shared';

export class ICNPush implements BaseEntity {
    constructor(
        public id?: number,
        public esn?: string,
        public datetime?: string,
        public vehicle?: BaseEntity,
        public crash?: BaseEntity,
    ) {
    }
}
