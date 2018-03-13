import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeImputation } from './type-imputation.model';
import { TypeImputationService } from './type-imputation.service';

@Component({
    selector: 'jhi-type-imputation-detail',
    templateUrl: './type-imputation-detail.component.html'
})
export class TypeImputationDetailComponent implements OnInit, OnDestroy {

    typeImputation: TypeImputation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeImputationService: TypeImputationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeImputations();
    }

    load(id) {
        this.typeImputationService.find(id)
            .subscribe((typeImputationResponse: HttpResponse<TypeImputation>) => {
                this.typeImputation = typeImputationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeImputations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeImputationListModification',
            (response) => this.load(this.typeImputation.id)
        );
    }
}
