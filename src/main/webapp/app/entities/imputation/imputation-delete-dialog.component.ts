import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Imputation } from './imputation.model';
import { ImputationPopupService } from './imputation-popup.service';
import { ImputationService } from './imputation.service';

@Component({
    selector: 'jhi-imputation-delete-dialog',
    templateUrl: './imputation-delete-dialog.component.html'
})
export class ImputationDeleteDialogComponent {

    imputation: Imputation;

    constructor(
        private imputationService: ImputationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imputationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imputationListModification',
                content: 'Deleted an imputation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-imputation-delete-popup',
    template: ''
})
export class ImputationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imputationPopupService: ImputationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imputationPopupService
                .open(ImputationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
