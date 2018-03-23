import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TypeImputation, TypeImputationService } from '../../entities/type-imputation';
import { ImputationPopupService } from '../../entities/imputation/imputation-popup.service';

@Component({
    selector: 'jhi-saisie-temps-dialog',
    templateUrl: './saisie-temps-dialog.component.html'
})
export class SaisieTempsDialogComponent implements OnInit {

    typeImputationSaisi: TypeImputation;
    typeImputations: TypeImputation[];

    constructor(
        public activeModal: NgbActiveModal,
        private typeImputationService: TypeImputationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {}

    getAllTypesImputation() {
        this.typeImputationService.query().subscribe(
            (res: HttpResponse<TypeImputation[]>) => {
                this.typeImputations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.getAllTypesImputation();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        console.log('Type imputation : id ' + this.typeImputationSaisi.id
                + ' | code : ' + this.typeImputationSaisi.code
                + ' | libellé : ' + this.typeImputationSaisi.libelle);
        this.eventManager.broadcast({ name: 'nouveauTypeImputation', content: this.typeImputationSaisi.code});
        this.activeModal.close();
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-saisie-temps-popup',
    template: ''
})
export class SaisieTempsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imputationPopupService: ImputationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imputationPopupService
                    .open(SaisieTempsDialogComponent as Component, params['id']);
            } else {
                this.imputationPopupService
                    .open(SaisieTempsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
