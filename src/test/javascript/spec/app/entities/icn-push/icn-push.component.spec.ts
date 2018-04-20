/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IcnpushTestModule } from '../../../test.module';
import { ICNPushComponent } from '../../../../../../main/webapp/app/entities/icn-push/icn-push.component';
import { ICNPushService } from '../../../../../../main/webapp/app/entities/icn-push/icn-push.service';
import { ICNPush } from '../../../../../../main/webapp/app/entities/icn-push/icn-push.model';

describe('Component Tests', () => {

    describe('ICNPush Management Component', () => {
        let comp: ICNPushComponent;
        let fixture: ComponentFixture<ICNPushComponent>;
        let service: ICNPushService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [ICNPushComponent],
                providers: [
                    ICNPushService
                ]
            })
            .overrideTemplate(ICNPushComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ICNPushComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ICNPushService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ICNPush(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.iCNPushes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
