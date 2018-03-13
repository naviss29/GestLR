/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestLrTestModule } from '../../../test.module';
import { SignatureComponent } from '../../../../../../main/webapp/app/entities/signature/signature.component';
import { SignatureService } from '../../../../../../main/webapp/app/entities/signature/signature.service';
import { Signature } from '../../../../../../main/webapp/app/entities/signature/signature.model';

describe('Component Tests', () => {

    describe('Signature Management Component', () => {
        let comp: SignatureComponent;
        let fixture: ComponentFixture<SignatureComponent>;
        let service: SignatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GestLrTestModule],
                declarations: [SignatureComponent],
                providers: [
                    SignatureService
                ]
            })
            .overrideTemplate(SignatureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SignatureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SignatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Signature(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.signatures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
