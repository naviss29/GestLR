import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentaireService } from './commentaire.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-commentaire',
    templateUrl: './commentaire.component.html'
})
export class CommentaireComponent implements OnInit, OnDestroy {
commentaires: Commentaire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commentaireService: CommentaireService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commentaireService.query().subscribe(
            (res: HttpResponse<Commentaire[]>) => {
                this.commentaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommentaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Commentaire) {
        return item.id;
    }
    registerChangeInCommentaires() {
        this.eventSubscriber = this.eventManager.subscribe('commentaireListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
