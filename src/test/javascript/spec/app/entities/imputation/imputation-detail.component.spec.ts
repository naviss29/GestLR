/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GestLrTestModule } from '../../../test.module';
import { ImputationDetailComponent } from '../../../../../../main/webapp/app/entities/imputation/imputation-detail.component';
import { ImputationService } from '../../../../../../main/webapp/app/entities/imputation/imputation.service';
import { Imputation } from '../../../../../../main/webapp/app/entities/imputation/imputation.model';

describe('Component Tests', () => {

    describe('Imputation Management Detail Component', () => {
        let comp: ImputationDetailComponent;
        let fixture: ComponentFixture<ImputationDetailComponent>;
        let service: ImputationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [ImputationDetailComponent],
                providers: [
                    ImputationService
                ]
            })
            .overrideTemplate(ImputationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImputationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImputationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Imputation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.imputation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
