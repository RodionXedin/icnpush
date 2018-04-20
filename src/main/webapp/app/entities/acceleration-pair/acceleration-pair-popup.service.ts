import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AccelerationPair } from './acceleration-pair.model';
import { AccelerationPairService } from './acceleration-pair.service';

@Injectable()
export class AccelerationPairPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private accelerationPairService: AccelerationPairService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.accelerationPairService.find(id)
                    .subscribe((accelerationPairResponse: HttpResponse<AccelerationPair>) => {
                        const accelerationPair: AccelerationPair = accelerationPairResponse.body;
                        this.ngbModalRef = this.accelerationPairModalRef(component, accelerationPair);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.accelerationPairModalRef(component, new AccelerationPair());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    accelerationPairModalRef(component: Component, accelerationPair: AccelerationPair): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.accelerationPair = accelerationPair;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
