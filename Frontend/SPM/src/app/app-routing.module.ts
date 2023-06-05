import { DeleteAllDisabledAdminComponent } from './components/admins/delete-all-disabled-admin/delete-all-disabled-admin.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//Common Components
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';
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
import { OrganizersMenuComponent } from './pages/organizers-menu/organizers-menu.component';
import { EventsOrganizerComponent } from './components/organizers/events-organizer/events-organizer.component';
import { TournamentsOrganizerComponent } from './components/organizers/tournaments-organizer/tournaments-organizer.component';
import { RegisterTournamentOrganizerComponent } from './components/organizers/register-tournament-organizer/register-tournament-organizer.component';
import { RegisterEventOrganizerComponent } from './components/organizers/register-event-organizer/register-event-organizer.component';
//Guards
import { AuthGuard } from './guards/auth/auth.guard';
import { AthleteGuard } from './guards/athlete/athlete.guard';
import { OrganizerGuard } from './guards/organizer/organizer.guard';
import { AdminGuard } from './guards/admin/admin.guard';
//Admin Components
import { AdminsMenuComponent } from './pages/admins-menu/admins-menu.component';
import { AthletesAdminComponent } from './components/admins/athletes-admin/athletes-admin.component';
import { OrganizersAdminComponent } from './components/admins/organizers-admin/organizers-admin.component';
import { EventsAdminComponent } from './components/admins/events-admin/events-admin.component';
import { TournamentsAdminComponent } from './components/admins/tournaments-admin/tournaments-admin.component';
import { WatchlistsAdminComponent } from './components/admins/watchlists-admin/watchlists-admin.component';
import { InscriptionsAdminComponent } from './components/admins/inscriptions-admin/inscriptions-admin.component';
import { SportsTypesAdminComponent } from './components/admins/sports-types-admin/sports-types-admin.component';

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
  {
    path: 'admins-menu',
    component: AdminsMenuComponent,
    canActivate: [AuthGuard, AdminGuard],
    children: [
      {
        path: 'welcome',
        component: WelcomeComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'athletes',
        component: AthletesAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'organizers',
        component: OrganizersAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'events',
        component: EventsAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'tournaments',
        component: TournamentsAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'watchlists',
        component: WatchlistsAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'inscriptions',
        component: InscriptionsAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'sports_types',
        component: SportsTypesAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
      },
      {
        path: 'delete_disableds',
        component: DeleteAllDisabledAdminComponent,
        canActivate: [AuthGuard, AdminGuard],
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
