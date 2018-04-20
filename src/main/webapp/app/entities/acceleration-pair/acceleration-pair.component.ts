import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AccelerationPair } from './acceleration-pair.model';
import { AccelerationPairService } from './acceleration-pair.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-acceleration-pair',
    templateUrl: './acceleration-pair.component.html'
})
export class AccelerationPairComponent implements OnInit, OnDestroy {
accelerationPairs: AccelerationPair[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private accelerationPairService: AccelerationPairService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.accelerationPairService.query().subscribe(
            (res: HttpResponse<AccelerationPair[]>) => {
                this.accelerationPairs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAccelerationPairs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AccelerationPair) {
        return item.id;
    }
    registerChangeInAccelerationPairs() {
        this.eventSubscriber = this.eventManager.subscribe('accelerationPairListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
