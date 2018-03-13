/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { CommentaireComponent } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.component';
import { CommentaireService } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.service';
import { Commentaire } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.model';

describe('Component Tests', () => {

    describe('Commentaire Management Component', () => {
        let comp: CommentaireComponent;
        let fixture: ComponentFixture<CommentaireComponent>;
        let service: CommentaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [CommentaireComponent],
                providers: [
                    CommentaireService
                ]
            })
            .overrideTemplate(CommentaireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentaireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Commentaire(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commentaires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
