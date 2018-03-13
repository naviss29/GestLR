/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { TypeImputationComponent } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.component';
import { TypeImputationService } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.service';
import { TypeImputation } from '../../../../../../main/webapp/app/entities/type-imputation/type-imputation.model';

describe('Component Tests', () => {

    describe('TypeImputation Management Component', () => {
        let comp: TypeImputationComponent;
        let fixture: ComponentFixture<TypeImputationComponent>;
        let service: TypeImputationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeImputationComponent],
                providers: [
                    TypeImputationService
                ]
            })
            .overrideTemplate(TypeImputationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeImputationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeImputationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeImputation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeImputations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
