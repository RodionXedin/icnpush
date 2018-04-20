/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IcnpushTestModule } from '../../../test.module';
import { CrashComponent } from '../../../../../../main/webapp/app/entities/crash/crash.component';
import { CrashService } from '../../../../../../main/webapp/app/entities/crash/crash.service';
import { Crash } from '../../../../../../main/webapp/app/entities/crash/crash.model';

describe('Component Tests', () => {

    describe('Crash Management Component', () => {
        let comp: CrashComponent;
        let fixture: ComponentFixture<CrashComponent>;
        let service: CrashService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [CrashComponent],
                providers: [
                    CrashService
                ]
            })
            .overrideTemplate(CrashComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CrashComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CrashService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Crash(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.crashes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
