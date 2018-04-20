import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Crash } from './crash.model';
import { CrashService } from './crash.service';

@Component({
    selector: 'jhi-crash-detail',
    templateUrl: './crash-detail.component.html'
})
export class CrashDetailComponent implements OnInit, OnDestroy {

    crash: Crash;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private crashService: CrashService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCrashes();
    }

    load(id) {
        this.crashService.find(id)
            .subscribe((crashResponse: HttpResponse<Crash>) => {
                this.crash = crashResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCrashes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'crashListModification',
            (response) => this.load(this.crash.id)
        );
    }
}
