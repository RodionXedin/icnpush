import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AccelerationPair } from './acceleration-pair.model';
import { AccelerationPairPopupService } from './acceleration-pair-popup.service';
import { AccelerationPairService } from './acceleration-pair.service';

@Component({
    selector: 'jhi-acceleration-pair-dialog',
    templateUrl: './acceleration-pair-dialog.component.html'
})
export class AccelerationPairDialogComponent implements OnInit {

    accelerationPair: AccelerationPair;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private accelerationPairService: AccelerationPairService,
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
        if (this.accelerationPair.id !== undefined) {
            this.subscribeToSaveResponse(
                this.accelerationPairService.update(this.accelerationPair));
        } else {
            this.subscribeToSaveResponse(
                this.accelerationPairService.create(this.accelerationPair));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AccelerationPair>>) {
        result.subscribe((res: HttpResponse<AccelerationPair>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AccelerationPair) {
        this.eventManager.broadcast({ name: 'accelerationPairListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-acceleration-pair-popup',
    template: ''
})
export class AccelerationPairPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accelerationPairPopupService: AccelerationPairPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.accelerationPairPopupService
                    .open(AccelerationPairDialogComponent as Component, params['id']);
            } else {
                this.accelerationPairPopupService
                    .open(AccelerationPairDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
