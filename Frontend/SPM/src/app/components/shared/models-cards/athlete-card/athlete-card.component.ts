import { AuthService } from './../../../../services/auth/auth-service.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AthleteServiceService } from 'src/app/services/athlete/athlete-service.service';
import { Athlete } from 'src/app/interfaces/athlete/Athlete';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpPasswordComponent } from '../../pop-ups/pop-up-password/pop-up-password.component';
import { PopUpMsgComponent } from '../../pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-athlete-card',
  templateUrl: './athlete-card.component.html',
  styleUrls: ['./athlete-card.component.scss'],
})
export class AthleteCardComponent {
  athlete: Athlete = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    phone_number: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private athleteService: AthleteServiceService,
    private AuthService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.athleteService.getAthlete(this.AuthService.getId()).subscribe(
      (response) => {
        this.athlete = {
          id: response.id,
          username: response.username,
          nif: response.nif,
          email: response.email,
          first_name: response.first_name,
          last_name: response.last_name,
          phone_number: response.phone_number,
        };

        console.log(this.athlete);

        this.form.setValue({
          username: this.athlete.username,
          nif: this.athlete.nif,
          email: this.athlete.email,
          first_name: this.athlete.first_name,
          last_name: this.athlete.last_name,
          phone_number: this.athlete.phone_number,
        });
      },
      (error) => {
        console.log(error);
        this.router.navigate(['/login']);
      }
    );
    this.form = this.fb.group({
      username: new FormControl('', [Validators.required]),
      nif: new FormControl(this.athlete.nif, [
        Validators.required,
        Validators.pattern('[0-9]{8}-[A-Z]{1}'),
      ]),
      email: new FormControl('', [Validators.required, Validators.email]),
      first_name: new FormControl('', [
        Validators.pattern('[a-zA-Z ]*'),
        Validators.required,
      ]),
      last_name: new FormControl('', [
        Validators.pattern('[a-zA-Z ]*'),
        Validators.required,
      ]),
      phone_number: new FormControl('', [
        Validators.pattern('[- +()0-9]{15,15}'),
        Validators.required,
      ]),
    });
  }

  get username() {
    return this.form.get('username');
  }
  get nif() {
    return this.form.get('nif');
  }

  get email() {
    return this.form.get('email');
  }

  get first_name() {
    return this.form.get('first_name');
  }

  get last_name() {
    return this.form.get('last_name');
  }

  get phone_number() {
    return this.form.get('phone_number');
  }

  changePassword() {
    this.dialog
      .open(PopUpPasswordComponent, { data: true })
      .afterClosed()
      .subscribe((result) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'La contraseña se ha cambiado correctamente, por favor, vuelva a iniciar sesión.',
          },
        });
        this.logout();
      });
  }

  back(): void {
    this.AuthService.getPath();
  }

  logout(): void {
    this.AuthService.logout();
  }

  unsubscribe(): void {
    this.athleteService.deleteAthlete(this.athlete.id).subscribe(
      (response) => {
        this.logout();
        this.back();
      },
      (error) => {
        console.log('Atleta: ' + error);
      }
    );
  }

  ngOnSubmit(): void {
    this.athlete = {
      id: this.athlete.id,
      username: this.form.value.username,
      nif: this.form.value.nif,
      email: this.form.value.email,
      first_name: this.form.value.first_name,
      last_name: this.form.value.last_name,
      phone_number: this.form.value.phone_number,
    };

    this.athleteService.updateAthlete(this.athlete).subscribe(
      (response) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            register: true,
            msg: 'Los datos han sido modificados, por favor, vuelva a iniciar sesión.',
          },
        });
        this.logout();
      },
      (error) => {
        console.log(error);
        if (error.status == 403) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              register: false,
              msg: 'Error al actualizar.',
            },
          });
        }
      }
    );
  }
}
