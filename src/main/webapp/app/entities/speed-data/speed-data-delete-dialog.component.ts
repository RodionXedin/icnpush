import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SpeedData } from './speed-data.model';
import { SpeedDataPopupService } from './speed-data-popup.service';
import { SpeedDataService } from './speed-data.service';

@Component({
    selector: 'jhi-speed-data-delete-dialog',
    templateUrl: './speed-data-delete-dialog.component.html'
})
export class SpeedDataDeleteDialogComponent {

    speedData: SpeedData;

    constructor(
        private speedDataService: SpeedDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.speedDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'speedDataListModification',
                content: 'Deleted an speedData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-speed-data-delete-popup',
    template: ''
})
export class SpeedDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private speedDataPopupService: SpeedDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.speedDataPopupService
                .open(SpeedDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
