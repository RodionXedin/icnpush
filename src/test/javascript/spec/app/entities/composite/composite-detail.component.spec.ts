/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IcnpushTestModule } from '../../../test.module';
import { CompositeDetailComponent } from '../../../../../../main/webapp/app/entities/composite/composite-detail.component';
import { CompositeService } from '../../../../../../main/webapp/app/entities/composite/composite.service';
import { Composite } from '../../../../../../main/webapp/app/entities/composite/composite.model';

describe('Component Tests', () => {

    describe('Composite Management Detail Component', () => {
        let comp: CompositeDetailComponent;
        let fixture: ComponentFixture<CompositeDetailComponent>;
        let service: CompositeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [CompositeDetailComponent],
                providers: [
                    CompositeService
                ]
            })
            .overrideTemplate(CompositeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompositeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompositeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Composite(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.composite).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
