import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { AuthService } from "../../../features/authentification/service/auth.service";
import { Router } from "@angular/router";
import { SessionService } from "../../services/session.service";
import { User } from "../../models/user.interface";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    public menuIsVisible: boolean = false;
    public isLogged$: Observable<boolean>;

    constructor(
        private authService: AuthService,
        public router: Router,
        private sessionService: SessionService
    ) {
        this.isLogged$ = this.sessionService.$isLogged();
        router.events.subscribe((val) => {
            this.menuIsVisible = false;
        });

    }

    ngOnInit(): void {
        this.autoLog();
    }

    public currentUrl(): string {
        return this.router.url;
    }

    public toggleMenu() {
        this.menuIsVisible = !this.menuIsVisible;
    }
    

    public logout(): void {
        this.sessionService.logOut();
        this.router.navigate(['/']);
    }

    public autoLog(): void {
        this.authService.me().subscribe(
            (user: User) => {
                this.sessionService.logIn(user);
            },
            (_) => {
                this.sessionService.logOut();
            }
        );
    }
}
