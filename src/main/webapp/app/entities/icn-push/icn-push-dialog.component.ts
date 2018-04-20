import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICNPush } from './icn-push.model';
import { ICNPushPopupService } from './icn-push-popup.service';
import { ICNPushService } from './icn-push.service';
import { Vehicle, VehicleService } from '../vehicle';
import { Crash, CrashService } from '../crash';

@Component({
    selector: 'jhi-icn-push-dialog',
    templateUrl: './icn-push-dialog.component.html'
})
export class ICNPushDialogComponent implements OnInit {

    iCNPush: ICNPush;
    isSaving: boolean;

    vehicles: Vehicle[];

    crashes: Crash[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private iCNPushService: ICNPushService,
        private vehicleService: VehicleService,
        private crashService: CrashService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.vehicleService
            .query({filter: 'icnpush-is-null'})
            .subscribe((res: HttpResponse<Vehicle[]>) => {
                if (!this.iCNPush.vehicle || !this.iCNPush.vehicle.id) {
                    this.vehicles = res.body;
                } else {
                    this.vehicleService
                        .find(this.iCNPush.vehicle.id)
                        .subscribe((subRes: HttpResponse<Vehicle>) => {
                            this.vehicles = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.crashService
            .query({filter: 'icnpush-is-null'})
            .subscribe((res: HttpResponse<Crash[]>) => {
                if (!this.iCNPush.crash || !this.iCNPush.crash.id) {
                    this.crashes = res.body;
                } else {
                    this.crashService
                        .find(this.iCNPush.crash.id)
                        .subscribe((subRes: HttpResponse<Crash>) => {
                            this.crashes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.iCNPush.id !== undefined) {
            this.subscribeToSaveResponse(
                this.iCNPushService.update(this.iCNPush));
        } else {
            this.subscribeToSaveResponse(
                this.iCNPushService.create(this.iCNPush));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICNPush>>) {
        result.subscribe((res: HttpResponse<ICNPush>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ICNPush) {
        this.eventManager.broadcast({ name: 'iCNPushListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVehicleById(index: number, item: Vehicle) {
        return item.id;
    }

    trackCrashById(index: number, item: Crash) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-icn-push-popup',
    template: ''
})
export class ICNPushPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iCNPushPopupService: ICNPushPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.iCNPushPopupService
                    .open(ICNPushDialogComponent as Component, params['id']);
            } else {
                this.iCNPushPopupService
                    .open(ICNPushDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
