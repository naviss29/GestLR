import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Periode } from './periode.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Periode>;

@Injectable()
export class PeriodeService {

    private resourceUrl =  SERVER_API_URL + 'api/periodes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(periode: Periode): Observable<EntityResponseType> {
        const copy = this.convert(periode);
        return this.http.post<Periode>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(periode: Periode): Observable<EntityResponseType> {
        const copy = this.convert(periode);
        return this.http.put<Periode>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Periode>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Periode[]>> {
        const options = createRequestOption(req);
        return this.http.get<Periode[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Periode[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Periode = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Periode[]>): HttpResponse<Periode[]> {
        const jsonResponse: Periode[] = res.body;
        const body: Periode[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Periode.
     */
    private convertItemFromServer(periode: Periode): Periode {
        const copy: Periode = Object.assign({}, periode);
        copy.echeance = this.dateUtils
            .convertDateTimeFromServer(periode.echeance);
        return copy;
    }

    /**
     * Convert a Periode to a JSON which can be sent to the server.
     */
    private convert(periode: Periode): Periode {
        const copy: Periode = Object.assign({}, periode);

        copy.echeance = this.dateUtils.toDate(periode.echeance);
        return copy;
    }
}
