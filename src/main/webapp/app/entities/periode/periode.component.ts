import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Periode } from './periode.model';
import { PeriodeService } from './periode.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-periode',
    templateUrl: './periode.component.html'
})
export class PeriodeComponent implements OnInit, OnDestroy {
periodes: Periode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private periodeService: PeriodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.periodeService.query().subscribe(
            (res: HttpResponse<Periode[]>) => {
                this.periodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Periode) {
        return item.id;
    }
    registerChangeInPeriodes() {
        this.eventSubscriber = this.eventManager.subscribe('periodeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
