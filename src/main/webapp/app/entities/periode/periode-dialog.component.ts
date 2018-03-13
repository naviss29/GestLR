import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Periode } from './periode.model';
import { PeriodePopupService } from './periode-popup.service';
import { PeriodeService } from './periode.service';

@Component({
    selector: 'jhi-periode-dialog',
    templateUrl: './periode-dialog.component.html'
})
export class PeriodeDialogComponent implements OnInit {

    periode: Periode;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private periodeService: PeriodeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.periode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.periodeService.update(this.periode));
        } else {
            this.subscribeToSaveResponse(
                this.periodeService.create(this.periode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Periode>>) {
        result.subscribe((res: HttpResponse<Periode>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Periode) {
        this.eventManager.broadcast({ name: 'periodeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-periode-popup',
    template: ''
})
export class PeriodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodePopupService: PeriodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.periodePopupService
                    .open(PeriodeDialogComponent as Component, params['id']);
            } else {
                this.periodePopupService
                    .open(PeriodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
