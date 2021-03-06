import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Vehicle } from './vehicle.model';
import { VehiclePopupService } from './vehicle-popup.service';
import { VehicleService } from './vehicle.service';

@Component({
    selector: 'jhi-vehicle-dialog',
    templateUrl: './vehicle-dialog.component.html'
})
export class VehicleDialogComponent implements OnInit {

    vehicle: Vehicle;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private vehicleService: VehicleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehicle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehicleService.update(this.vehicle));
        } else {
            this.subscribeToSaveResponse(
                this.vehicleService.create(this.vehicle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Vehicle>>) {
        result.subscribe((res: HttpResponse<Vehicle>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Vehicle) {
        this.eventManager.broadcast({ name: 'vehicleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-vehicle-popup',
    template: ''
})
export class VehiclePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiclePopupService: VehiclePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vehiclePopupService
                    .open(VehicleDialogComponent as Component, params['id']);
            } else {
                this.vehiclePopupService
                    .open(VehicleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
