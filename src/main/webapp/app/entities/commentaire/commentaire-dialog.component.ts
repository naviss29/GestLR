import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentairePopupService } from './commentaire-popup.service';
import { CommentaireService } from './commentaire.service';
import { Periode, PeriodeService } from '../periode';

@Component({
    selector: 'jhi-commentaire-dialog',
    templateUrl: './commentaire-dialog.component.html'
})
export class CommentaireDialogComponent implements OnInit {

    commentaire: Commentaire;
    isSaving: boolean;

    periodes: Periode[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commentaireService: CommentaireService,
        private periodeService: PeriodeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodeService.query()
            .subscribe((res: HttpResponse<Periode[]>) => { this.periodes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commentaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentaireService.update(this.commentaire));
        } else {
            this.subscribeToSaveResponse(
                this.commentaireService.create(this.commentaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Commentaire>>) {
        result.subscribe((res: HttpResponse<Commentaire>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Commentaire) {
        this.eventManager.broadcast({ name: 'commentaireListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-commentaire-popup',
    template: ''
})
export class CommentairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentairePopupService: CommentairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentairePopupService
                    .open(CommentaireDialogComponent as Component, params['id']);
            } else {
                this.commentairePopupService
                    .open(CommentaireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
