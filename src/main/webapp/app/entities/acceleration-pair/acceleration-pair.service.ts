import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AccelerationPair } from './acceleration-pair.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AccelerationPair>;

@Injectable()
export class AccelerationPairService {

    private resourceUrl =  SERVER_API_URL + 'api/acceleration-pairs';

    constructor(private http: HttpClient) { }

    create(accelerationPair: AccelerationPair): Observable<EntityResponseType> {
        const copy = this.convert(accelerationPair);
        return this.http.post<AccelerationPair>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(accelerationPair: AccelerationPair): Observable<EntityResponseType> {
        const copy = this.convert(accelerationPair);
        return this.http.put<AccelerationPair>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AccelerationPair>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AccelerationPair[]>> {
        const options = createRequestOption(req);
        return this.http.get<AccelerationPair[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AccelerationPair[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AccelerationPair = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AccelerationPair[]>): HttpResponse<AccelerationPair[]> {
        const jsonResponse: AccelerationPair[] = res.body;
        const body: AccelerationPair[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AccelerationPair.
     */
    private convertItemFromServer(accelerationPair: AccelerationPair): AccelerationPair {
        const copy: AccelerationPair = Object.assign({}, accelerationPair);
        return copy;
    }

    /**
     * Convert a AccelerationPair to a JSON which can be sent to the server.
     */
    private convert(accelerationPair: AccelerationPair): AccelerationPair {
        const copy: AccelerationPair = Object.assign({}, accelerationPair);
        return copy;
    }
}
