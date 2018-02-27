import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import {TypeConge} from '../../entities/type-conge/type-conge.model';
import {createRequestOption} from '../../shared';

@Injectable()
export class CongesService {

    private resourceUrl =  SERVER_API_URL + 'api/type-conges';

    constructor(private http: HttpClient) {}

    save(newPassword: string): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/account/change-password', newPassword);
    }

    queryTypeConges(req?: any): Observable<HttpResponse<TypeConge[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeConge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeConge[]>) => this.convertArrayResponse(res));
    }

    private convertArrayResponse(res: HttpResponse<TypeConge[]>): HttpResponse<TypeConge[]> {
        const jsonResponse: TypeConge[] = res.body;
        const body: TypeConge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    private convertItemFromServer(typeConge: TypeConge): TypeConge {
        const copy: TypeConge = Object.assign({}, typeConge);
        return copy;
    }

}
