/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IcnpushTestModule } from '../../../test.module';
import { SpeedDataDetailComponent } from '../../../../../../main/webapp/app/entities/speed-data/speed-data-detail.component';
import { SpeedDataService } from '../../../../../../main/webapp/app/entities/speed-data/speed-data.service';
import { SpeedData } from '../../../../../../main/webapp/app/entities/speed-data/speed-data.model';

describe('Component Tests', () => {

    describe('SpeedData Management Detail Component', () => {
        let comp: SpeedDataDetailComponent;
        let fixture: ComponentFixture<SpeedDataDetailComponent>;
        let service: SpeedDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [SpeedDataDetailComponent],
                providers: [
                    SpeedDataService
                ]
            })
            .overrideTemplate(SpeedDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpeedDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeedDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SpeedData(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.speedData).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
