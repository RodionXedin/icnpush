import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Composite } from './composite.model';
import { CompositeService } from './composite.service';

@Component({
    selector: 'jhi-composite-detail',
    templateUrl: './composite-detail.component.html'
})
export class CompositeDetailComponent implements OnInit, OnDestroy {

    composite: Composite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private compositeService: CompositeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComposites();
    }

    load(id) {
        this.compositeService.find(id)
            .subscribe((compositeResponse: HttpResponse<Composite>) => {
                this.composite = compositeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInComposites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'compositeListModification',
            (response) => this.load(this.composite.id)
        );
    }
}
