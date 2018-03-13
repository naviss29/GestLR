/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GestLrTestModule } from '../../../test.module';
import { PeriodeDetailComponent } from '../../../../../../main/webapp/app/entities/periode/periode-detail.component';
import { PeriodeService } from '../../../../../../main/webapp/app/entities/periode/periode.service';
import { Periode } from '../../../../../../main/webapp/app/entities/periode/periode.model';

describe('Component Tests', () => {

    describe('Periode Management Detail Component', () => {
        let comp: PeriodeDetailComponent;
        let fixture: ComponentFixture<PeriodeDetailComponent>;
        let service: PeriodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [PeriodeDetailComponent],
                providers: [
                    PeriodeService
                ]
            })
            .overrideTemplate(PeriodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Periode(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.periode).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
