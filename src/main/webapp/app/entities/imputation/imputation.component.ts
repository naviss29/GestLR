import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Imputation } from './imputation.model';
import { ImputationService } from './imputation.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-imputation',
    templateUrl: './imputation.component.html'
})
export class ImputationComponent implements OnInit, OnDestroy {
imputations: Imputation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private imputationService: ImputationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.imputationService.query().subscribe(
            (res: HttpResponse<Imputation[]>) => {
                this.imputations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInImputations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Imputation) {
        return item.id;
    }
    registerChangeInImputations() {
        this.eventSubscriber = this.eventManager.subscribe('imputationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
