import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AccelerationPair } from './acceleration-pair.model';
import { AccelerationPairService } from './acceleration-pair.service';

@Component({
    selector: 'jhi-acceleration-pair-detail',
    templateUrl: './acceleration-pair-detail.component.html'
})
export class AccelerationPairDetailComponent implements OnInit, OnDestroy {

    accelerationPair: AccelerationPair;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private accelerationPairService: AccelerationPairService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAccelerationPairs();
    }

    load(id) {
        this.accelerationPairService.find(id)
            .subscribe((accelerationPairResponse: HttpResponse<AccelerationPair>) => {
                this.accelerationPair = accelerationPairResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAccelerationPairs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'accelerationPairListModification',
            (response) => this.load(this.accelerationPair.id)
        );
    }
}
