import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ICNPush } from './icn-push.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ICNPush>;

@Injectable()
export class ICNPushService {

    private resourceUrl =  SERVER_API_URL + 'api/icn-pushes';

    constructor(private http: HttpClient) { }

    create(iCNPush: ICNPush): Observable<EntityResponseType> {
        const copy = this.convert(iCNPush);
        return this.http.post<ICNPush>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(iCNPush: ICNPush): Observable<EntityResponseType> {
        const copy = this.convert(iCNPush);
        return this.http.put<ICNPush>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICNPush>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ICNPush[]>> {
        const options = createRequestOption(req);
        return this.http.get<ICNPush[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ICNPush[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ICNPush = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ICNPush[]>): HttpResponse<ICNPush[]> {
        const jsonResponse: ICNPush[] = res.body;
        const body: ICNPush[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ICNPush.
     */
    private convertItemFromServer(iCNPush: ICNPush): ICNPush {
        const copy: ICNPush = Object.assign({}, iCNPush);
        return copy;
    }

    /**
     * Convert a ICNPush to a JSON which can be sent to the server.
     */
    private convert(iCNPush: ICNPush): ICNPush {
        const copy: ICNPush = Object.assign({}, iCNPush);
        return copy;
    }
}
