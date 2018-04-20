import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SpeedData } from './speed-data.model';
import { SpeedDataPopupService } from './speed-data-popup.service';
import { SpeedDataService } from './speed-data.service';

@Component({
    selector: 'jhi-speed-data-dialog',
    templateUrl: './speed-data-dialog.component.html'
})
export class SpeedDataDialogComponent implements OnInit {

    speedData: SpeedData;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private speedDataService: SpeedDataService,
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
        if (this.speedData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.speedDataService.update(this.speedData));
        } else {
            this.subscribeToSaveResponse(
                this.speedDataService.create(this.speedData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SpeedData>>) {
        result.subscribe((res: HttpResponse<SpeedData>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SpeedData) {
        this.eventManager.broadcast({ name: 'speedDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-speed-data-popup',
    template: ''
})
export class SpeedDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private speedDataPopupService: SpeedDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.speedDataPopupService
                    .open(SpeedDataDialogComponent as Component, params['id']);
            } else {
                this.speedDataPopupService
                    .open(SpeedDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
