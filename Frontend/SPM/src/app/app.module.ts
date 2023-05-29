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
import { PopUpRegisterComponent } from './components/shared/pop-ups/pop-up-register/pop-up-register.component';
import { RegisterOrganizerComponent } from './components/shared/forms/register-organizer/register-organizer.component';
import { WelcomeComponent } from './components/shared/welcome/welcome.component';
//Pages imports
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { AboutusPageComponent } from './pages/aboutus-page/aboutus-page.component';
//Athlete components imports
import { NavbarAthleteComponent } from './components/athletes/navbar-athlete/navbar-athlete.component';
import { WatchlistComponent } from './components/athletes/watchlist/watchlist.component';
import { InscriptionsComponent } from './components/athletes/inscriptions/inscriptions.component';
import { TournamentsComponent } from './components/athletes/tournaments/tournaments.component';
import { EventsComponent } from './components/athletes/events/events.component';
import { ProfileComponent } from './components/athletes/profile/profile.component';
import { AthletesMenuComponent } from './pages/athletes-menu/athletes-menu.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    LoginPageComponent,
    OrganizerRegisterComponent,
    AthleteRegisterComponent,
    LoginFormComponent,
    RegisterAthleteComponent,
    PopUpRegisterComponent,
    RegisterOrganizerComponent,
    AboutusPageComponent,
    NavbarAthleteComponent,
    WelcomeComponent,
    WatchlistComponent,
    InscriptionsComponent,
    TournamentsComponent,
    EventsComponent,
    ProfileComponent,
    AthletesMenuComponent,
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
