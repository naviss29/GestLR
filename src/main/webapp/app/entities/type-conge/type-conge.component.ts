import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TypeConge } from './type-conge.model';
import { TypeCongeService } from './type-conge.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-type-conge',
    templateUrl: './type-conge.component.html'
})
export class TypeCongeComponent implements OnInit, OnDestroy {
typeConges: TypeConge[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeCongeService: TypeCongeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.typeCongeService.query().subscribe(
            (res: HttpResponse<TypeConge[]>) => {
                this.typeConges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeConges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TypeConge) {
        return item.id;
    }
    registerChangeInTypeConges() {
        this.eventSubscriber = this.eventManager.subscribe('typeCongeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
