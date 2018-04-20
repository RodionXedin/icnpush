/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { IcnpushTestModule } from '../../../test.module';
import { CompositeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/composite/composite-delete-dialog.component';
import { CompositeService } from '../../../../../../main/webapp/app/entities/composite/composite.service';

describe('Component Tests', () => {

    describe('Composite Management Delete Component', () => {
        let comp: CompositeDeleteDialogComponent;
        let fixture: ComponentFixture<CompositeDeleteDialogComponent>;
        let service: CompositeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IcnpushTestModule],
                declarations: [CompositeDeleteDialogComponent],
                providers: [
                    CompositeService
                ]
            })
            .overrideTemplate(CompositeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompositeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompositeService);
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
