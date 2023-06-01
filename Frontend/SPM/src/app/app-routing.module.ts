import { Athlete } from './interfaces/athlete/Athlete';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// Shared Components
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';
import { WelcomeComponent } from './components/shared/welcome/welcome.component';
// Athlete Components
import { AthletesMenuComponent } from './pages/athletes-menu/athletes-menu.component';
import { WatchlistComponent } from './components/athletes/watchlist/watchlist.component';
import { InscriptionsComponent } from './components/athletes/inscriptions/inscriptions.component';
import { TournamentsComponent } from './components/athletes/tournaments/tournaments.component';
import { EventsComponent } from './components/athletes/events/events.component';
import { ProfileComponent } from './components/athletes/profile/profile.component';
//Guards
import { AuthGuard } from './guards/auth/auth.guard';
import { AthleteGuard } from './guards/athlete/athlete.guard';
import { AthleteCardComponent } from './components/shared/models-cards/athlete-card/athlete-card.component';
import { EventCardComponent } from './components/shared/models-cards/event-card/event-card.component';

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
  {
    path: 'athletes-menu',
    component: AthletesMenuComponent,
    canActivate: [AuthGuard, AthleteGuard],
    children: [
      {
        path: 'welcome',
        component: WelcomeComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'watchlist',
        component: WatchlistComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'inscriptions',
        component: InscriptionsComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'tournaments',
        component: TournamentsComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'events',
        component: EventsComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'profile',
        component: AthleteCardComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'event-card',
        component: EventCardComponent,
        canActivate: [AuthGuard],
      },
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
