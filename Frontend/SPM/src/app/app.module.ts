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
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { OrganizerRegisterComponent } from './pages/organizer-register/organizer-register.component';
import { AthleteRegisterComponent } from './pages/athlete-register/athlete-register.component';
import { LoginFormComponent } from './components/forms/login-form/login-form.component';
import { RegisterAthleteComponent } from './components/forms/register-athlete/register-athlete.component';
import { PopUpRegisterComponent } from './components/pop-ups/pop-up-register/pop-up-register.component';
import { RegisterOrganizerComponent } from './components/forms/register-organizer/register-organizer.component';

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
