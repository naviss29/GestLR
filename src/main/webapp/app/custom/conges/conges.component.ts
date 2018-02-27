import { Component, OnInit } from '@angular/core';

import { Principal } from '../../shared';
import { CongesService } from './conges.service';
import { Conges } from '../../shared/model/conges';
import { TypeConge } from '../../entities/type-conge/type-conge.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-conges',
    templateUrl: './conges.component.html'
})
export class CongesComponent implements OnInit {

    account: any;
    typeConges: any;
    submitted = false;

    constructor(
        private congesService: CongesService,
        private jhiAlertService: JhiAlertService,
        private principal: Principal
    ) {
    }

    load_all() {
        this.congesService.queryTypeConges().subscribe(
            (res: HttpResponse<TypeConge[]>) => {
                this.typeConges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.load_all();

        console.log(this.typeConges);
    }

    onSubmit() { this.submitted = true; }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
