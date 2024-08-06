import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from "../home/home.component";

const routes: Routes = [
   {title: 'Accueil | MondeDeDév', path: '', component: HomeComponent},
   {title: 'Se connecter | MondeDeDév', path: 'login', component: LoginComponent},
   {title: 'S\'inscrire | MondeDeDév', path: 'register', component: RegisterComponent},
];

@NgModule({
   imports: [RouterModule.forChild(routes)],
   exports: [RouterModule]
})
export class AuthRoutingModule {
}
