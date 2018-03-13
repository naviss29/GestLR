import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Commentaire>;

@Injectable()
export class CommentaireService {

    private resourceUrl =  SERVER_API_URL + 'api/commentaires';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(commentaire: Commentaire): Observable<EntityResponseType> {
        const copy = this.convert(commentaire);
        return this.http.post<Commentaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(commentaire: Commentaire): Observable<EntityResponseType> {
        const copy = this.convert(commentaire);
        return this.http.put<Commentaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Commentaire>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Commentaire[]>> {
        const options = createRequestOption(req);
        return this.http.get<Commentaire[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Commentaire[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Commentaire = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Commentaire[]>): HttpResponse<Commentaire[]> {
        const jsonResponse: Commentaire[] = res.body;
        const body: Commentaire[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Commentaire.
     */
    private convertItemFromServer(commentaire: Commentaire): Commentaire {
        const copy: Commentaire = Object.assign({}, commentaire);
        copy.dateSaisie = this.dateUtils
            .convertDateTimeFromServer(commentaire.dateSaisie);
        return copy;
    }

    /**
     * Convert a Commentaire to a JSON which can be sent to the server.
     */
    private convert(commentaire: Commentaire): Commentaire {
        const copy: Commentaire = Object.assign({}, commentaire);

        copy.dateSaisie = this.dateUtils.toDate(commentaire.dateSaisie);
        return copy;
    }
}
