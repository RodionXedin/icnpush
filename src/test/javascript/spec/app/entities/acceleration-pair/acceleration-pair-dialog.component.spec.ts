/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { IcnpushTestModule } from '../../../test.module';
import { AccelerationPairDialogComponent } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair-dialog.component';
import { AccelerationPairService } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.service';
import { AccelerationPair } from '../../../../../../main/webapp/app/entities/acceleration-pair/acceleration-pair.model';

describe('Component Tests', () => {

    describe('AccelerationPair Management Dialog Component', () => {
        let comp: AccelerationPairDialogComponent;
        let fixture: ComponentFixture<AccelerationPairDialogComponent>;
        let service: AccelerationPairService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [AccelerationPairDialogComponent],
                providers: [
                    AccelerationPairService
                ]
            })
            .overrideTemplate(AccelerationPairDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccelerationPairDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccelerationPairService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AccelerationPair(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.accelerationPair = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'accelerationPairListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AccelerationPair();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.accelerationPair = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'accelerationPairListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
