/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { IcnpushTestModule } from '../../../test.module';
import { AccelerationPairDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair-delete-dialog.component';
import { AccelerationPairService } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.service';

describe('Component Tests', () => {

    describe('AccelerationPair Management Delete Component', () => {
        let comp: AccelerationPairDeleteDialogComponent;
        let fixture: ComponentFixture<AccelerationPairDeleteDialogComponent>;
        let service: AccelerationPairService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [AccelerationPairDeleteDialogComponent],
                providers: [
                    AccelerationPairService
                ]
            })
            .overrideTemplate(AccelerationPairDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccelerationPairDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccelerationPairService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
