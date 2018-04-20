import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Crash } from './crash.model';
import { CrashPopupService } from './crash-popup.service';
import { CrashService } from './crash.service';

@Component({
    selector: 'jhi-crash-dialog',
    templateUrl: './crash-dialog.component.html'
})
export class CrashDialogComponent implements OnInit {

    crash: Crash;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private crashService: CrashService,
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
        if (this.crash.id !== undefined) {
            this.subscribeToSaveResponse(
                this.crashService.update(this.crash));
        } else {
            this.subscribeToSaveResponse(
                this.crashService.create(this.crash));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Crash>>) {
        result.subscribe((res: HttpResponse<Crash>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Crash) {
        this.eventManager.broadcast({ name: 'crashListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-crash-popup',
    template: ''
})
export class CrashPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private crashPopupService: CrashPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.crashPopupService
                    .open(CrashDialogComponent as Component, params['id']);
            } else {
                this.crashPopupService
                    .open(CrashDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
