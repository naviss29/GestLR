import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeConge } from './type-conge.model';
import { TypeCongePopupService } from './type-conge-popup.service';
import { TypeCongeService } from './type-conge.service';

@Component({
    selector: 'jhi-type-conge-dialog',
    templateUrl: './type-conge-dialog.component.html'
})
export class TypeCongeDialogComponent implements OnInit {

    typeConge: TypeConge;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeCongeService: TypeCongeService,
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
        if (this.typeConge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeCongeService.update(this.typeConge));
        } else {
            this.subscribeToSaveResponse(
                this.typeCongeService.create(this.typeConge));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeConge>>) {
        result.subscribe((res: HttpResponse<TypeConge>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeConge) {
        this.eventManager.broadcast({ name: 'typeCongeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-conge-popup',
    template: ''
})
export class TypeCongePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeCongePopupService: TypeCongePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeCongePopupService
                    .open(TypeCongeDialogComponent as Component, params['id']);
            } else {
                this.typeCongePopupService
                    .open(TypeCongeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
