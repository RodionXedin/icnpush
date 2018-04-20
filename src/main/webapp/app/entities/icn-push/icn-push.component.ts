import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICNPush } from './icn-push.model';
import { ICNPushService } from './icn-push.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-icn-push',
    templateUrl: './icn-push.component.html'
})
export class ICNPushComponent implements OnInit, OnDestroy {
iCNPushes: ICNPush[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private iCNPushService: ICNPushService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.iCNPushService.query().subscribe(
            (res: HttpResponse<ICNPush[]>) => {
                this.iCNPushes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInICNPushes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICNPush) {
        return item.id;
    }
    registerChangeInICNPushes() {
        this.eventSubscriber = this.eventManager.subscribe('iCNPushListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
