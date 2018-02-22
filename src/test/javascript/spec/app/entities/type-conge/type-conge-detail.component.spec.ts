/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GestLrTestModule } from '../../../test.module';
import { TypeCongeDetailComponent } from '../../../../../../main/webapp/app/entities/type-conge/type-conge-detail.component';
import { TypeCongeService } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.service';
import { TypeConge } from '../../../../../../main/webapp/app/entities/type-conge/type-conge.model';

describe('Component Tests', () => {

    describe('TypeConge Management Detail Component', () => {
        let comp: TypeCongeDetailComponent;
        let fixture: ComponentFixture<TypeCongeDetailComponent>;
        let service: TypeCongeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [TypeCongeDetailComponent],
                providers: [
                    TypeCongeService
                ]
            })
            .overrideTemplate(TypeCongeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCongeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCongeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeConge(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeConge).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
