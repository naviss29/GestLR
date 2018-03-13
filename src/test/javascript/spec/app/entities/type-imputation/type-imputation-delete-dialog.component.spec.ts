/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GestLrTestModule } from '../../../test.module';
import { TypeImputationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation-delete-dialog.component';
import { TypeImputationService } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.service';

describe('Component Tests', () => {

    describe('TypeImputation Management Delete Component', () => {
        let comp: TypeImputationDeleteDialogComponent;
        let fixture: ComponentFixture<TypeImputationDeleteDialogComponent>;
        let service: TypeImputationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeImputationDeleteDialogComponent],
                providers: [
                    TypeImputationService
                ]
            })
            .overrideTemplate(TypeImputationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeImputationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeImputationService);
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
