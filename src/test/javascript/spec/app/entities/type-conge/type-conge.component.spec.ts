/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { TypeCongeComponent } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.component';
import { TypeCongeService } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.service';
import { TypeConge } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.model';

describe('Component Tests', () => {

    describe('TypeConge Management Component', () => {
        let comp: TypeCongeComponent;
        let fixture: ComponentFixture<TypeCongeComponent>;
        let service: TypeCongeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeCongeComponent],
                providers: [
                    TypeCongeService
                ]
            })
            .overrideTemplate(TypeCongeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCongeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCongeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeConge(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeConges[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
