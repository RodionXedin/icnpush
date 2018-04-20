import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Crash } from './crash.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Crash>;

@Injectable()
export class CrashService {

    private resourceUrl =  SERVER_API_URL + 'api/crashes';

    constructor(private http: HttpClient) { }

    create(crash: Crash): Observable<EntityResponseType> {
        const copy = this.convert(crash);
        return this.http.post<Crash>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(crash: Crash): Observable<EntityResponseType> {
        const copy = this.convert(crash);
        return this.http.put<Crash>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Crash>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Crash[]>> {
        const options = createRequestOption(req);
        return this.http.get<Crash[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Crash[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Crash = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Crash[]>): HttpResponse<Crash[]> {
        const jsonResponse: Crash[] = res.body;
        const body: Crash[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Crash.
     */
    private convertItemFromServer(crash: Crash): Crash {
        const copy: Crash = Object.assign({}, crash);
        return copy;
    }

    /**
     * Convert a Crash to a JSON which can be sent to the server.
     */
    private convert(crash: Crash): Crash {
        const copy: Crash = Object.assign({}, crash);
        return copy;
    }
}
