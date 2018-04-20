import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Composite } from './composite.model';
import { CompositePopupService } from './composite-popup.service';
import { CompositeService } from './composite.service';

@Component({
    selector: 'jhi-composite-delete-dialog',
    templateUrl: './composite-delete-dialog.component.html'
})
export class CompositeDeleteDialogComponent {

    composite: Composite;

    constructor(
        private compositeService: CompositeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compositeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'compositeListModification',
                content: 'Deleted an composite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-composite-delete-popup',
    template: ''
})
export class CompositeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private compositePopupService: CompositePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.compositePopupService
                .open(CompositeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
