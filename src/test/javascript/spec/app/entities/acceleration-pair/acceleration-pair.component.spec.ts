/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IcnpushTestModule } from '../../../test.module';
import { AccelerationPairComponent } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.component';
import { AccelerationPairService } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.service';
import { AccelerationPair } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.model';

describe('Component Tests', () => {

    describe('AccelerationPair Management Component', () => {
        let comp: AccelerationPairComponent;
        let fixture: ComponentFixture<AccelerationPairComponent>;
        let service: AccelerationPairService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [AccelerationPairComponent],
                providers: [
                    AccelerationPairService
                ]
            })
            .overrideTemplate(AccelerationPairComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccelerationPairComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccelerationPairService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AccelerationPair(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.accelerationPairs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
