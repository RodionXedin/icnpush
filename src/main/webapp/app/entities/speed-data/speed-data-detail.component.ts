import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SpeedData } from './speed-data.model';
import { SpeedDataService } from './speed-data.service';

@Component({
    selector: 'jhi-speed-data-detail',
    templateUrl: './speed-data-detail.component.html'
})
export class SpeedDataDetailComponent implements OnInit, OnDestroy {

    speedData: SpeedData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private speedDataService: SpeedDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpeedData();
    }

    load(id) {
        this.speedDataService.find(id)
            .subscribe((speedDataResponse: HttpResponse<SpeedData>) => {
                this.speedData = speedDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpeedData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'speedDataListModification',
            (response) => this.load(this.speedData.id)
        );
    }
}
