import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Commentaire } from './commentaire.model';
import { CommentaireService } from './commentaire.service';

@Injectable()
export class CommentairePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private commentaireService: CommentaireService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.commentaireService.find(id)
                    .subscribe((commentaireResponse: HttpResponse<Commentaire>) => {
                        const commentaire: Commentaire = commentaireResponse.body;
                        commentaire.dateSaisie = this.datePipe
                            .transform(commentaire.dateSaisie, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.commentaireModalRef(component, commentaire);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.commentaireModalRef(component, new Commentaire());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    commentaireModalRef(component: Component, commentaire: Commentaire): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.commentaire = commentaire;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
