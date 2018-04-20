/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IcnpushTestModule } from '../../../test.module';
import { AccelerationPairDetailComponent } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair-detail.component';
import { AccelerationPairService } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.service';
import { AccelerationPair } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.model';

describe('Component Tests', () => {

    describe('AccelerationPair Management Detail Component', () => {
        let comp: AccelerationPairDetailComponent;
        let fixture: ComponentFixture<AccelerationPairDetailComponent>;
        let service: AccelerationPairService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [AccelerationPairDetailComponent],
                providers: [
                    AccelerationPairService
                ]
            })
            .overrideTemplate(AccelerationPairDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccelerationPairDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccelerationPairService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AccelerationPair(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.accelerationPair).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
