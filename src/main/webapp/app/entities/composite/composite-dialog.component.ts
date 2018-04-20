import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Composite } from './composite.model';
import { CompositePopupService } from './composite-popup.service';
import { CompositeService } from './composite.service';

@Component({
    selector: 'jhi-composite-dialog',
    templateUrl: './composite-dialog.component.html'
})
export class CompositeDialogComponent implements OnInit {

    composite: Composite;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private compositeService: CompositeService,
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
        if (this.composite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.compositeService.update(this.composite));
        } else {
            this.subscribeToSaveResponse(
                this.compositeService.create(this.composite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Composite>>) {
        result.subscribe((res: HttpResponse<Composite>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Composite) {
        this.eventManager.broadcast({ name: 'compositeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-composite-popup',
    template: ''
})
export class CompositePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compositePopupService: CompositePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.compositePopupService
                    .open(CompositeDialogComponent as Component, params['id']);
            } else {
                this.compositePopupService
                    .open(CompositeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
