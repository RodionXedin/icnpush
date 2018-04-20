/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IcnpushTestModule } from '../../../test.module';
import { CrashDetailComponent } from '../../../../../../main/webapp/app/entities/crash/crash-detail.component';
import { CrashService } from '../../../../../../main/webapp/app/entities/crash/crash.service';
import { Crash } from '../../../../../../main/webapp/app/entities/crash/crash.model';

describe('Component Tests', () => {

    describe('Crash Management Detail Component', () => {
        let comp: CrashDetailComponent;
        let fixture: ComponentFixture<CrashDetailComponent>;
        let service: CrashService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [CrashDetailComponent],
                providers: [
                    CrashService
                ]
            })
            .overrideTemplate(CrashDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CrashDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CrashService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Crash(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.crash).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
