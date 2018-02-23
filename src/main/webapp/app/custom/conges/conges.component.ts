import { Component, OnInit } from '@angular/core';

import { Principal } from '../../shared';
import { CongesService } from './conges.service';

@Component({
    selector: 'jhi-conges',
    templateUrl: './conges.component.html'
})
export class CongesComponent implements OnInit {
    account: any;
    submitted = false;

    constructor(
        private congesService: CongesService,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    onSubmit() { this.submitted = true; }
}
