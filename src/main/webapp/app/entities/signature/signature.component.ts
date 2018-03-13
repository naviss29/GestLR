import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Signature } from './signature.model';
import { SignatureService } from './signature.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-signature',
    templateUrl: './signature.component.html'
})
export class SignatureComponent implements OnInit, OnDestroy {
signatures: Signature[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private signatureService: SignatureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.signatureService.query().subscribe(
            (res: HttpResponse<Signature[]>) => {
                this.signatures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSignatures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Signature) {
        return item.id;
    }
    registerChangeInSignatures() {
        this.eventSubscriber = this.eventManager.subscribe('signatureListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
