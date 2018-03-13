/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GestLrTestModule } from '../../../test.module';
import { TypeImputationDetailComponent } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation-detail.component';
import { TypeImputationService } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.service';
import { TypeImputation } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.model';

describe('Component Tests', () => {

    describe('TypeImputation Management Detail Component', () => {
        let comp: TypeImputationDetailComponent;
        let fixture: ComponentFixture<TypeImputationDetailComponent>;
        let service: TypeImputationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeImputationDetailComponent],
                providers: [
                    TypeImputationService
                ]
            })
            .overrideTemplate(TypeImputationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeImputationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeImputationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeImputation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeImputation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
