import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from "rxjs";
import { Router } from "@angular/router";
import { SessionService } from "../../services/session.service";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
/**
 * Composant d'en-tête
 * @class
 * @implements {OnInit}
 * @implements {OnDestroy}
 * @public
 */
export class HeaderComponent implements OnInit, OnDestroy{
    /**
     * Indique si le menu est visible
     * @type {boolean}
     */
    public menuIsVisible: boolean = false;
    /**
     * Utilisateur
     * @type {User | undefined}
     */
    public isLogged$: Observable<boolean> = new Observable<boolean>();
    /**
     * Subscription au router
     * @type {Subscription}
     */
    private routerSubscription: Subscription = new Subscription();

    constructor(
        public router: Router,
        private sessionService: SessionService
    ) {}

    ngOnInit(): void {
        this.isLogged$ = this.sessionService.$isLogged();
        this.routerSubscription.add(this.router.events.subscribe((val) => {
            this.menuIsVisible = false;
        }));
    }

    ngOnDestroy(): void {
        this.routerSubscription.unsubscribe();
    }

    /**
     * Retourne l'url courante
     * @returns {string}
     */
    public currentUrl(): string {
        return this.router.url;
    }

    /**
     * Bascule le menu
     */
    public toggleMenu() {
        this.menuIsVisible = !this.menuIsVisible;
    }
    
    /**
     * Déconnecte l'utilisateur
     */
    public logout(): void {
        this.sessionService.logOut();
        this.router.navigate(['/']);
    }
}