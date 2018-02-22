import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeConge } from './type-conge.model';
import { TypeCongeService } from './type-conge.service';

@Component({
    selector: 'jhi-type-conge-detail',
    templateUrl: './type-conge-detail.component.html'
})
export class TypeCongeDetailComponent implements OnInit, OnDestroy {

    typeConge: TypeConge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeCongeService: TypeCongeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeConges();
    }

    load(id) {
        this.typeCongeService.find(id)
            .subscribe((typeCongeResponse: HttpResponse<TypeConge>) => {
                this.typeConge = typeCongeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeConges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeCongeListModification',
            (response) => this.load(this.typeConge.id)
        );
    }
}
