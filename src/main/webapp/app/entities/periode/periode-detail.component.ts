import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Periode } from './periode.model';
import { PeriodeService } from './periode.service';

@Component({
    selector: 'jhi-periode-detail',
    templateUrl: './periode-detail.component.html'
})
export class PeriodeDetailComponent implements OnInit, OnDestroy {

    periode: Periode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private periodeService: PeriodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeriodes();
    }

    load(id) {
        this.periodeService.find(id)
            .subscribe((periodeResponse: HttpResponse<Periode>) => {
                this.periode = periodeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeriodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'periodeListModification',
            (response) => this.load(this.periode.id)
        );
    }
}
