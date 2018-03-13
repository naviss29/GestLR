import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Signature } from './signature.model';
import { SignaturePopupService } from './signature-popup.service';
import { SignatureService } from './signature.service';

@Component({
    selector: 'jhi-signature-delete-dialog',
    templateUrl: './signature-delete-dialog.component.html'
})
export class SignatureDeleteDialogComponent {

    signature: Signature;

    constructor(
        private signatureService: SignatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.signatureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'signatureListModification',
                content: 'Deleted an signature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-signature-delete-popup',
    template: ''
})
export class SignatureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signaturePopupService: SignaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.signaturePopupService
                .open(SignatureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
