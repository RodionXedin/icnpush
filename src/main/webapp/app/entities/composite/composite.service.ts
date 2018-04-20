import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Composite } from './composite.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Composite>;

@Injectable()
export class CompositeService {

    private resourceUrl =  SERVER_API_URL + 'api/composites';

    constructor(private http: HttpClient) { }

    create(composite: Composite): Observable<EntityResponseType> {
        const copy = this.convert(composite);
        return this.http.post<Composite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(composite: Composite): Observable<EntityResponseType> {
        const copy = this.convert(composite);
        return this.http.put<Composite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Composite>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Composite[]>> {
        const options = createRequestOption(req);
        return this.http.get<Composite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Composite[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Composite = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Composite[]>): HttpResponse<Composite[]> {
        const jsonResponse: Composite[] = res.body;
        const body: Composite[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Composite.
     */
    private convertItemFromServer(composite: Composite): Composite {
        const copy: Composite = Object.assign({}, composite);
        return copy;
    }

    /**
     * Convert a Composite to a JSON which can be sent to the server.
     */
    private convert(composite: Composite): Composite {
        const copy: Composite = Object.assign({}, composite);
        return copy;
    }
}
