import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Crash } from './crash.model';
import { CrashService } from './crash.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-crash',
    templateUrl: './crash.component.html'
})
export class CrashComponent implements OnInit, OnDestroy {
crashes: Crash[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private crashService: CrashService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.crashService.query().subscribe(
            (res: HttpResponse<Crash[]>) => {
                this.crashes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCrashes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Crash) {
        return item.id;
    }
    registerChangeInCrashes() {
        this.eventSubscriber = this.eventManager.subscribe('crashListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
