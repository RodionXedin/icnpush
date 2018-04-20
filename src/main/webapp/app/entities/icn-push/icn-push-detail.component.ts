import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ICNPush } from './icn-push.model';
import { ICNPushService } from './icn-push.service';

@Component({
    selector: 'jhi-icn-push-detail',
    templateUrl: './icn-push-detail.component.html'
})
export class ICNPushDetailComponent implements OnInit, OnDestroy {

    iCNPush: ICNPush;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private iCNPushService: ICNPushService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInICNPushes();
    }

    load(id) {
        this.iCNPushService.find(id)
            .subscribe((iCNPushResponse: HttpResponse<ICNPush>) => {
                this.iCNPush = iCNPushResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInICNPushes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'iCNPushListModification',
            (response) => this.load(this.iCNPush.id)
        );
    }
}
