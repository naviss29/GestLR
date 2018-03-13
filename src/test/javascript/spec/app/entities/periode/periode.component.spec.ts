/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { PeriodeComponent } from '../../../../../../main/webapp/app/entities/periode/periode.component';
import { PeriodeService } from '../../../../../../main/webapp/app/entities/periode/periode.service';
import { Periode } from '../../../../../../main/webapp/app/entities/periode/periode.model';

describe('Component Tests', () => {

    describe('Periode Management Component', () => {
        let comp: PeriodeComponent;
        let fixture: ComponentFixture<PeriodeComponent>;
        let service: PeriodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [PeriodeComponent],
                providers: [
                    PeriodeService
                ]
            })
            .overrideTemplate(PeriodeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Periode(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.periodes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
