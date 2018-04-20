import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICNPush } from './icn-push.model';
import { ICNPushPopupService } from './icn-push-popup.service';
import { ICNPushService } from './icn-push.service';

@Component({
    selector: 'jhi-icn-push-delete-dialog',
    templateUrl: './icn-push-delete-dialog.component.html'
})
export class ICNPushDeleteDialogComponent {

    iCNPush: ICNPush;

    constructor(
        private iCNPushService: ICNPushService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.iCNPushService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'iCNPushListModification',
                content: 'Deleted an iCNPush'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-icn-push-delete-popup',
    template: ''
})
export class ICNPushDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private iCNPushPopupService: ICNPushPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.iCNPushPopupService
                .open(ICNPushDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
