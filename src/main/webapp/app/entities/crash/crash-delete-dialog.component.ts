import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Crash } from './crash.model';
import { CrashPopupService } from './crash-popup.service';
import { CrashService } from './crash.service';

@Component({
    selector: 'jhi-crash-delete-dialog',
    templateUrl: './crash-delete-dialog.component.html'
})
export class CrashDeleteDialogComponent {

    crash: Crash;

    constructor(
        private crashService: CrashService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.crashService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'crashListModification',
                content: 'Deleted an crash'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-crash-delete-popup',
    template: ''
})
export class CrashDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private crashPopupService: CrashPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.crashPopupService
                .open(CrashDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
