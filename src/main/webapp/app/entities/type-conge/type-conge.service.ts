import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeConge } from './type-conge.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeConge>;

@Injectable()
export class TypeCongeService {

    private resourceUrl =  SERVER_API_URL + 'api/type-conges';

    constructor(private http: HttpClient) { }

    create(typeConge: TypeConge): Observable<EntityResponseType> {
        const copy = this.convert(typeConge);
        return this.http.post<TypeConge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeConge: TypeConge): Observable<EntityResponseType> {
        const copy = this.convert(typeConge);
        return this.http.put<TypeConge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeConge>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeConge[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeConge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeConge[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeConge = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeConge[]>): HttpResponse<TypeConge[]> {
        const jsonResponse: TypeConge[] = res.body;
        const body: TypeConge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeConge.
     */
    private convertItemFromServer(typeConge: TypeConge): TypeConge {
        const copy: TypeConge = Object.assign({}, typeConge);
        return copy;
    }

    /**
     * Convert a TypeConge to a JSON which can be sent to the server.
     */
    private convert(typeConge: TypeConge): TypeConge {
        const copy: TypeConge = Object.assign({}, typeConge);
        return copy;
    }
}
