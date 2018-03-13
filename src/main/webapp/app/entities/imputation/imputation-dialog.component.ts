import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Imputation } from './imputation.model';
import { ImputationPopupService } from './imputation-popup.service';
import { ImputationService } from './imputation.service';
import { Periode, PeriodeService } from '../periode';
import { TypeImputation, TypeImputationService } from '../type-imputation';

@Component({
    selector: 'jhi-imputation-dialog',
    templateUrl: './imputation-dialog.component.html'
})
export class ImputationDialogComponent implements OnInit {

    imputation: Imputation;
    isSaving: boolean;

    periodes: Periode[];

    types: TypeImputation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private imputationService: ImputationService,
        private periodeService: PeriodeService,
        private typeImputationService: TypeImputationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodeService.query()
            .subscribe((res: HttpResponse<Periode[]>) => { this.periodes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typeImputationService
            .query({filter: 'imputation-is-null'})
            .subscribe((res: HttpResponse<TypeImputation[]>) => {
                if (!this.imputation.typeId) {
                    this.types = res.body;
                } else {
                    this.typeImputationService
                        .find(this.imputation.typeId)
                        .subscribe((subRes: HttpResponse<TypeImputation>) => {
                            this.types = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.imputation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imputationService.update(this.imputation));
        } else {
            this.subscribeToSaveResponse(
                this.imputationService.create(this.imputation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Imputation>>) {
        result.subscribe((res: HttpResponse<Imputation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Imputation) {
        this.eventManager.broadcast({ name: 'imputationListModification', content: 'OK'});
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

    trackTypeImputationById(index: number, item: TypeImputation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-imputation-popup',
    template: ''
})
export class ImputationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imputationPopupService: ImputationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imputationPopupService
                    .open(ImputationDialogComponent as Component, params['id']);
            } else {
                this.imputationPopupService
                    .open(ImputationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
