import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Periode } from './periode.model';
import { PeriodePopupService } from './periode-popup.service';
import { PeriodeService } from './periode.service';

@Component({
    selector: 'jhi-periode-delete-dialog',
    templateUrl: './periode-delete-dialog.component.html'
})
export class PeriodeDeleteDialogComponent {

    periode: Periode;

    constructor(
        private periodeService: PeriodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'periodeListModification',
                content: 'Deleted an periode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-periode-delete-popup',
    template: ''
})
export class PeriodeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodePopupService: PeriodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.periodePopupService
                .open(PeriodeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
