import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Imputation } from './imputation.model';
import { ImputationService } from './imputation.service';

@Component({
    selector: 'jhi-imputation-detail',
    templateUrl: './imputation-detail.component.html'
})
export class ImputationDetailComponent implements OnInit, OnDestroy {

    imputation: Imputation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private imputationService: ImputationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInImputations();
    }

    load(id) {
        this.imputationService.find(id)
            .subscribe((imputationResponse: HttpResponse<Imputation>) => {
                this.imputation = imputationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInImputations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'imputationListModification',
            (response) => this.load(this.imputation.id)
        );
    }
}
