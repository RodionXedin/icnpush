/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IcnpushTestModule } from '../../../test.module';
import { CompositeComponent } from '../../../../../../main/webapp/app/entities/composite/composite.component';
import { CompositeService } from '../../../../../../main/webapp/app/entities/composite/composite.service';
import { Composite } from '../../../../../../main/webapp/app/entities/composite/composite.model';

describe('Component Tests', () => {

    describe('Composite Management Component', () => {
        let comp: CompositeComponent;
        let fixture: ComponentFixture<CompositeComponent>;
        let service: CompositeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [CompositeComponent],
                providers: [
                    CompositeService
                ]
            })
            .overrideTemplate(CompositeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompositeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompositeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Composite(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.composites[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
