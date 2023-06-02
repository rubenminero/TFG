//Modules imports
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MaterialModule } from './modules/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
//Components imports
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//Shared components imports
import { ToolbarComponent } from './components/shared/toolbar/toolbar.component';
import { LoginFormComponent } from './components/shared/forms/login-form/login-form.component';
import { RegisterAthleteComponent } from './components/shared/forms/register-athlete/register-athlete.component';
import { RegisterOrganizerComponent } from './components/shared/forms/register-organizer/register-organizer.component';
import { WelcomeComponent } from './components/shared/welcome/welcome.component';
import { AthleteCardComponent } from './components/shared/models-cards/athlete-card/athlete-card.component';
import { PopUpPasswordComponent } from './components/shared/pop-ups/pop-up-password/pop-up-password.component';
//Pages imports
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';
//Athlete components imports
import { NavbarAthleteComponent } from './components/athletes/navbar-athlete/navbar-athlete.component';
import { WatchlistAthleteComponent } from './components/athletes/watchlist-athlete/watchlist-athlete.component';
import { InscriptionsAthleteComponent } from './components/athletes/inscriptions-athlete/inscriptions-athlete.component';
import { TournamentsAthleteComponent } from './components/athletes/tournaments-athlete/tournaments-athlete.component';
import { EventsAthleteComponent } from './components/athletes/events-athlete/events-athlete.component';
import { AthletesMenuComponent } from './pages/athletes-menu/athletes-menu.component';
import { TournamentCardComponent } from './components/shared/models-cards/tournament-card/tournament-card.component';
import { EventCardComponent } from './components/shared/models-cards/event-card/event-card.component';
import { PopUpMsgComponent } from './components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { NavbarOrganizerComponent } from './components/organizers/navbar-organizer/navbar-organizer.component';
import { OrganizersMenuComponent } from './pages/organizers-menu/organizers-menu.component';
import { OrganizerCardComponent } from './components/shared/models-cards/organizer-card/organizer-card.component';
import { ProfileOrganizerComponent } from './components/organizers/profile-organizer/profile-organizer.component';
import { ProfileAthleteComponent } from './components/athletes/profile-athlete/profile-athlete.component';
import { EventsOrganizerComponent } from './components/organizers/events-organizer/events-organizer.component';
import { TournamentsOrganizerComponent } from './components/organizers/tournaments-organizer/tournaments-organizer.component';
import { RegisterTournamentOrganizerComponent } from './components/organizers/register-tournament-organizer/register-tournament-organizer.component';
import { RegisterEventOrganizerComponent } from './components/organizers/register-event-organizer/register-event-organizer.component';
import { InscriptionsOrganizerComponent } from './components/organizers/inscriptions-organizer/inscriptions-organizer.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    LoginPageComponent,
    OrganizerRegisterComponent,
    AthleteRegisterComponent,
    LoginFormComponent,
    RegisterAthleteComponent,
    RegisterOrganizerComponent,
    AboutusPageComponent,
    NavbarAthleteComponent,
    WelcomeComponent,
    WatchlistAthleteComponent,
    InscriptionsAthleteComponent,
    TournamentsAthleteComponent,
    EventsAthleteComponent,
    AthletesMenuComponent,
    AthleteCardComponent,
    PopUpPasswordComponent,
    TournamentCardComponent,
    EventCardComponent,
    PopUpMsgComponent,
    NavbarOrganizerComponent,
    OrganizersMenuComponent,
    OrganizerCardComponent,
    ProfileOrganizerComponent,
    ProfileAthleteComponent,
    EventsOrganizerComponent,
    TournamentsOrganizerComponent,
    RegisterTournamentOrganizerComponent,
    RegisterEventOrganizerComponent,
    InscriptionsOrganizerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
