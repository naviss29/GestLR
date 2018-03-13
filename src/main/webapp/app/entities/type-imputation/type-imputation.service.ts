import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeImputation } from './type-imputation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeImputation>;

@Injectable()
export class TypeImputationService {

    private resourceUrl =  SERVER_API_URL + 'api/type-imputations';

    constructor(private http: HttpClient) { }

    create(typeImputation: TypeImputation): Observable<EntityResponseType> {
        const copy = this.convert(typeImputation);
        return this.http.post<TypeImputation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeImputation: TypeImputation): Observable<EntityResponseType> {
        const copy = this.convert(typeImputation);
        return this.http.put<TypeImputation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeImputation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeImputation[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeImputation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeImputation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeImputation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeImputation[]>): HttpResponse<TypeImputation[]> {
        const jsonResponse: TypeImputation[] = res.body;
        const body: TypeImputation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeImputation.
     */
    private convertItemFromServer(typeImputation: TypeImputation): TypeImputation {
        const copy: TypeImputation = Object.assign({}, typeImputation);
        return copy;
    }

    /**
     * Convert a TypeImputation to a JSON which can be sent to the server.
     */
    private convert(typeImputation: TypeImputation): TypeImputation {
        const copy: TypeImputation = Object.assign({}, typeImputation);
        return copy;
    }
}
