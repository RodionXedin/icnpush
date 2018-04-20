import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SpeedData } from './speed-data.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SpeedData>;

@Injectable()
export class SpeedDataService {

    private resourceUrl =  SERVER_API_URL + 'api/speed-data';

    constructor(private http: HttpClient) { }

    create(speedData: SpeedData): Observable<EntityResponseType> {
        const copy = this.convert(speedData);
        return this.http.post<SpeedData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(speedData: SpeedData): Observable<EntityResponseType> {
        const copy = this.convert(speedData);
        return this.http.put<SpeedData>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SpeedData>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SpeedData[]>> {
        const options = createRequestOption(req);
        return this.http.get<SpeedData[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SpeedData[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SpeedData = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SpeedData[]>): HttpResponse<SpeedData[]> {
        const jsonResponse: SpeedData[] = res.body;
        const body: SpeedData[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SpeedData.
     */
    private convertItemFromServer(speedData: SpeedData): SpeedData {
        const copy: SpeedData = Object.assign({}, speedData);
        return copy;
    }

    /**
     * Convert a SpeedData to a JSON which can be sent to the server.
     */
    private convert(speedData: SpeedData): SpeedData {
        const copy: SpeedData = Object.assign({}, speedData);
        return copy;
    }
}
