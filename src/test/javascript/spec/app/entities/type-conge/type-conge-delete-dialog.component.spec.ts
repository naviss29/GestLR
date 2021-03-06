/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GestLrTestModule } from '../../../test.module';
import { TypeCongeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/type-conge/type-conge-delete-dialog.component';
import { TypeCongeService } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.service';

describe('Component Tests', () => {

    describe('TypeConge Management Delete Component', () => {
        let comp: TypeCongeDeleteDialogComponent;
        let fixture: ComponentFixture<TypeCongeDeleteDialogComponent>;
        let service: TypeCongeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeCongeDeleteDialogComponent],
                providers: [
                    TypeCongeService
                ]
            })
            .overrideTemplate(TypeCongeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCongeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCongeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
