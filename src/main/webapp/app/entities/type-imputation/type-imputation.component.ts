import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TypeImputation } from './type-imputation.model';
import { TypeImputationService } from './type-imputation.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-type-imputation',
    templateUrl: './type-imputation.component.html'
})
export class TypeImputationComponent implements OnInit, OnDestroy {
typeImputations: TypeImputation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeImputationService: TypeImputationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.typeImputationService.query().subscribe(
            (res: HttpResponse<TypeImputation[]>) => {
                this.typeImputations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeImputations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TypeImputation) {
        return item.id;
    }
    registerChangeInTypeImputations() {
        this.eventSubscriber = this.eventManager.subscribe('typeImputationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
