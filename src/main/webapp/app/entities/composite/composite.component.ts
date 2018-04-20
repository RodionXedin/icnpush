import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Composite } from './composite.model';
import { CompositeService } from './composite.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-composite',
    templateUrl: './composite.component.html'
})
export class CompositeComponent implements OnInit, OnDestroy {
composites: Composite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private compositeService: CompositeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.compositeService.query().subscribe(
            (res: HttpResponse<Composite[]>) => {
                this.composites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInComposites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Composite) {
        return item.id;
    }
    registerChangeInComposites() {
        this.eventSubscriber = this.eventManager.subscribe('compositeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
