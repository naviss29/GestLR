import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Commentaire } from './commentaire.model';
import { CommentaireService } from './commentaire.service';

@Component({
    selector: 'jhi-commentaire-detail',
    templateUrl: './commentaire-detail.component.html'
})
export class CommentaireDetailComponent implements OnInit, OnDestroy {

    commentaire: Commentaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commentaireService: CommentaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentaires();
    }

    load(id) {
        this.commentaireService.find(id)
            .subscribe((commentaireResponse: HttpResponse<Commentaire>) => {
                this.commentaire = commentaireResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentaireListModification',
            (response) => this.load(this.commentaire.id)
        );
    }
}
