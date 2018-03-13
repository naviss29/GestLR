import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Signature } from './signature.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Signature>;

@Injectable()
export class SignatureService {

    private resourceUrl =  SERVER_API_URL + 'api/signatures';

    constructor(private http: HttpClient) { }

    create(signature: Signature): Observable<EntityResponseType> {
        const copy = this.convert(signature);
        return this.http.post<Signature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(signature: Signature): Observable<EntityResponseType> {
        const copy = this.convert(signature);
        return this.http.put<Signature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Signature>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Signature[]>> {
        const options = createRequestOption(req);
        return this.http.get<Signature[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Signature[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Signature = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Signature[]>): HttpResponse<Signature[]> {
        const jsonResponse: Signature[] = res.body;
        const body: Signature[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Signature.
     */
    private convertItemFromServer(signature: Signature): Signature {
        const copy: Signature = Object.assign({}, signature);
        return copy;
    }

    /**
     * Convert a Signature to a JSON which can be sent to the server.
     */
    private convert(signature: Signature): Signature {
        const copy: Signature = Object.assign({}, signature);
        return copy;
    }
}
