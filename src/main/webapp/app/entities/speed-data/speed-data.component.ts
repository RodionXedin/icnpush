import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SpeedData } from './speed-data.model';
import { SpeedDataService } from './speed-data.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-speed-data',
    templateUrl: './speed-data.component.html'
})
export class SpeedDataComponent implements OnInit, OnDestroy {
speedData: SpeedData[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private speedDataService: SpeedDataService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.speedDataService.query().subscribe(
            (res: HttpResponse<SpeedData[]>) => {
                this.speedData = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpeedData();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SpeedData) {
        return item.id;
    }
    registerChangeInSpeedData() {
        this.eventSubscriber = this.eventManager.subscribe('speedDataListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
