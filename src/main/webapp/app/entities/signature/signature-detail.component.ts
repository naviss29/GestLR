import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Signature } from './signature.model';
import { SignatureService } from './signature.service';

@Component({
    selector: 'jhi-signature-detail',
    templateUrl: './signature-detail.component.html'
})
export class SignatureDetailComponent implements OnInit, OnDestroy {

    signature: Signature;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private signatureService: SignatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSignatures();
    }

    load(id) {
        this.signatureService.find(id)
            .subscribe((signatureResponse: HttpResponse<Signature>) => {
                this.signature = signatureResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSignatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'signatureListModification',
            (response) => this.load(this.signature.id)
        );
    }
}
