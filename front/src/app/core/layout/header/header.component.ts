import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription, takeUntil } from "rxjs";
import { AuthService } from "../../../features/authentification/service/auth.service";
import { Event, Router } from "@angular/router";
import { SessionService } from "../../services/session.service";
import { User } from "../../models/user.interface";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
/**
 * Composant pour l'en-tête de l'application
 */
export class HeaderComponent implements OnInit, OnDestroy {
    public menuIsVisible: boolean = false;
    public isLogged$: Observable<boolean>;

    // Subscription et Subject pour gérer la désinscription de l'observable
    private routerSubscription!: Subscription;
    private destroy$ = new Subject<void>();

    constructor(
        private authService: AuthService,
        public router: Router,
        private sessionService: SessionService
    ) {
        this.isLogged$ = this.sessionService.$isLogged();

        // Sauvegarde de la souscription pour pouvoir la désinscrire
        this.routerSubscription = router.events.subscribe((val: Event) => {
            this.menuIsVisible = false;
        });
    }

    ngOnInit(): void {
        this.autoLog();
    }

    ngOnDestroy(): void {
        // Unsubscribe from the router events
        if (this.routerSubscription) {
            this.routerSubscription.unsubscribe();
        }

        // Emit the destroy signal and complete the subject to clean up
        this.destroy$.next();
        this.destroy$.complete();
        console.log('HeaderComponent destroyed');
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
        this.authService.me().pipe(
            takeUntil(this.destroy$)  // Se désinscrire automatiquement à la destruction du composant
        ).subscribe({
            next: (user: User) => {
                this.sessionService.logIn(user);
            },
            error: (_) => {
                this.sessionService.logOut();
            }
        });
    }
}