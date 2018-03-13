/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { ImputationComponent } from '../../../../../../main/webapp/app/entities/imputation/imputation.component';
import { ImputationService } from '../../../../../../main/webapp/app/entities/imputation/imputation.service';
import { Imputation } from '../../../../../../main/webapp/app/entities/imputation/imputation.model';

describe('Component Tests', () => {

    describe('Imputation Management Component', () => {
        let comp: ImputationComponent;
        let fixture: ComponentFixture<ImputationComponent>;
        let service: ImputationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [ImputationComponent],
                providers: [
                    ImputationService
                ]
            })
            .overrideTemplate(ImputationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImputationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImputationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Imputation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.imputations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
