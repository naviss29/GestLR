import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeImputation } from './type-imputation.model';
import { TypeImputationPopupService } from './type-imputation-popup.service';
import { TypeImputationService } from './type-imputation.service';

@Component({
    selector: 'jhi-type-imputation-delete-dialog',
    templateUrl: './type-imputation-delete-dialog.component.html'
})
export class TypeImputationDeleteDialogComponent {

    typeImputation: TypeImputation;

    constructor(
        private typeImputationService: TypeImputationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeImputationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeImputationListModification',
                content: 'Deleted an typeImputation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-imputation-delete-popup',
    template: ''
})
export class TypeImputationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeImputationPopupService: TypeImputationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeImputationPopupService
                .open(TypeImputationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
