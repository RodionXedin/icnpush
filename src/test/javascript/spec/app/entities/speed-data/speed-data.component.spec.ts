/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IcnpushTestModule } from '../../../test.module';
import { SpeedDataComponent } from '../../../../../../main/webapp/app/entities/speed-data/speed-data.component';
import { SpeedDataService } from '../../../../../../main/webapp/app/entities/speed-data/speed-data.service';
import { SpeedData } from '../../../../../../main/webapp/app/entities/speed-data/speed-data.model';

describe('Component Tests', () => {

    describe('SpeedData Management Component', () => {
        let comp: SpeedDataComponent;
        let fixture: ComponentFixture<SpeedDataComponent>;
        let service: SpeedDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [SpeedDataComponent],
                providers: [
                    SpeedDataService
                ]
            })
            .overrideTemplate(SpeedDataComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpeedDataComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeedDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SpeedData(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.speedData[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
