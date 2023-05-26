import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login-page/login-page.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginPageComponent,
  },
  {
    path: 'register-athlete',
    component: AthleteRegisterComponent,
  },
  {
    path: 'register-organizer',
    component: OrganizerRegisterComponent,
  },
  {
    path: 'about-us',
    component: AboutusPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
