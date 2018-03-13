import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Signature } from './signature.model';
import { SignaturePopupService } from './signature-popup.service';
import { SignatureService } from './signature.service';
import { Periode, PeriodeService } from '../periode';

@Component({
    selector: 'jhi-signature-dialog',
    templateUrl: './signature-dialog.component.html'
})
export class SignatureDialogComponent implements OnInit {

    signature: Signature;
    isSaving: boolean;

    periodes: Periode[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private signatureService: SignatureService,
        private periodeService: PeriodeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodeService.query()
            .subscribe((res: HttpResponse<Periode[]>) => { this.periodes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.signature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.signatureService.update(this.signature));
        } else {
            this.subscribeToSaveResponse(
                this.signatureService.create(this.signature));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Signature>>) {
        result.subscribe((res: HttpResponse<Signature>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Signature) {
        this.eventManager.broadcast({ name: 'signatureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPeriodeById(index: number, item: Periode) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-signature-popup',
    template: ''
})
export class SignaturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signaturePopupService: SignaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.signaturePopupService
                    .open(SignatureDialogComponent as Component, params['id']);
            } else {
                this.signaturePopupService
                    .open(SignatureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
