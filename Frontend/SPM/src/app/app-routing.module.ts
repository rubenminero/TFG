import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginPageComponent } from './pages/login-page/login-page.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';
<<<<<<< Updated upstream
=======
import { WelcomeComponent } from './components/shared/welcome/welcome.component';
// Athlete Components
import { AthletesMenuComponent } from './pages/athletes-menu/athletes-menu.component';
import { WatchlistAthleteComponent } from './components/athletes/watchlist-athlete/watchlist-athlete.component';
import { InscriptionsAthleteComponent } from './components/athletes/inscriptions-athlete/inscriptions-athlete.component';
import { TournamentsAthleteComponent } from './components/athletes/tournaments-athlete/tournaments-athlete.component';
import { EventsAthleteComponent } from './components/athletes/events-athlete/events-athlete.component';
import { ProfileAthleteComponent } from './components/athletes/profile-athlete/profile-athlete.component';
// Organizer Components
import { ProfileOrganizerComponent } from './components/organizers/profile-organizer/profile-organizer.component';
//Guards
import { AuthGuard } from './guards/auth/auth.guard';
import { AthleteGuard } from './guards/athlete/athlete.guard';
import { AthleteCardComponent } from './components/shared/models-cards/athlete-card/athlete-card.component';
import { EventCardComponent } from './components/shared/models-cards/event-card/event-card.component';
import { OrganizersMenuComponent } from './pages/organizers-menu/organizers-menu.component';
import { OrganizerGuard } from './guards/organizer/organizer.guard';
import { EventsOrganizerComponent } from './components/organizers/events-organizer/events-organizer.component';
import { TournamentsOrganizerComponent } from './components/organizers/tournaments-organizer/tournaments-organizer.component';
import { RegisterTournamentOrganizerComponent } from './components/organizers/register-tournament-organizer/register-tournament-organizer.component';
import { RegisterEventOrganizerComponent } from './components/organizers/register-event-organizer/register-event-organizer.component';
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
=======
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
        component: WatchlistAthleteComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'inscriptions',
        component: InscriptionsAthleteComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'tournaments',
        component: TournamentsAthleteComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'events',
        component: EventsAthleteComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: 'profile',
        component: ProfileAthleteComponent,
        canActivate: [AuthGuard, AthleteGuard],
      },
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'organizers-menu',
    component: OrganizersMenuComponent,
    canActivate: [AuthGuard, OrganizerGuard],
    children: [
      {
        path: 'welcome',
        component: WelcomeComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: 'profile',
        component: ProfileOrganizerComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: 'events',
        component: EventsOrganizerComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: 'tournaments',
        component: TournamentsOrganizerComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: 'register-tournament',
        component: RegisterTournamentOrganizerComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: 'register-event',
        component: RegisterEventOrganizerComponent,
        canActivate: [AuthGuard, OrganizerGuard],
      },
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
    ],
  },
>>>>>>> Stashed changes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
