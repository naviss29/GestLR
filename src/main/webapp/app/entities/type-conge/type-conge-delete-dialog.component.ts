import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeConge } from './type-conge.model';
import { TypeCongePopupService } from './type-conge-popup.service';
import { TypeCongeService } from './type-conge.service';

@Component({
    selector: 'jhi-type-conge-delete-dialog',
    templateUrl: './type-conge-delete-dialog.component.html'
})
export class TypeCongeDeleteDialogComponent {

    typeConge: TypeConge;

    constructor(
        private typeCongeService: TypeCongeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeCongeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeCongeListModification',
                content: 'Deleted an typeConge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-conge-delete-popup',
    template: ''
})
export class TypeCongeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeCongePopupService: TypeCongePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeCongePopupService
                .open(TypeCongeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
