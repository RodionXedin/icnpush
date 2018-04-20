/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { IcnpushTestModule } from '../../../test.module';
import { ICNPushDialogComponent } from '../../../../../../main/webapp/app/entities/icn-push/icn-push-dialog.component';
import { ICNPushService } from '../../../../../../main/webapp/app/entities/icn-push/icn-push.service';
import { ICNPush } from '../../../../../../main/webapp/app/entities/icn-push/icn-push.model';
import { VehicleService } from '../../../../../../main/webapp/app/entities/vehicle';
import { CrashService } from '../../../../../../main/webapp/app/entities/crash';

describe('Component Tests', () => {

    describe('ICNPush Management Dialog Component', () => {
        let comp: ICNPushDialogComponent;
        let fixture: ComponentFixture<ICNPushDialogComponent>;
        let service: ICNPushService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [ICNPushDialogComponent],
                providers: [
                    VehicleService,
                    CrashService,
                    ICNPushService
                ]
            })
            .overrideTemplate(ICNPushDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ICNPushDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ICNPushService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ICNPush(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.iCNPush = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'iCNPushListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ICNPush();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.iCNPush = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'iCNPushListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
