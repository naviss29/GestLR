import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeImputation } from './type-imputation.model';
import { TypeImputationPopupService } from './type-imputation-popup.service';
import { TypeImputationService } from './type-imputation.service';

@Component({
    selector: 'jhi-type-imputation-dialog',
    templateUrl: './type-imputation-dialog.component.html'
})
export class TypeImputationDialogComponent implements OnInit {

    typeImputation: TypeImputation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeImputationService: TypeImputationService,
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
        if (this.typeImputation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeImputationService.update(this.typeImputation));
        } else {
            this.subscribeToSaveResponse(
                this.typeImputationService.create(this.typeImputation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeImputation>>) {
        result.subscribe((res: HttpResponse<TypeImputation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeImputation) {
        this.eventManager.broadcast({ name: 'typeImputationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-imputation-popup',
    template: ''
})
export class TypeImputationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeImputationPopupService: TypeImputationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeImputationPopupService
                    .open(TypeImputationDialogComponent as Component, params['id']);
            } else {
                this.typeImputationPopupService
                    .open(TypeImputationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
