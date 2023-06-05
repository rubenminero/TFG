import { AuthService } from './../../../../services/auth/auth-service.service';
import { Component, Input } from '@angular/core';
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
  @Input() admin: boolean = false;
  @Input() athlete: Athlete = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    phone_number: '',
    enabled: false,
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private dialog: MatDialog,
    private athleteService: AthleteServiceService,
    private AuthService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    if (this.athlete.id == -1) {
      this.dialog
        .open(PopUpMsgComponent, {
          data: {
            msg: 'Error al cargar los datos del atleta.',
          },
        })
        .afterClosed()
        .subscribe((result) => {
          this.AuthService.getPath();
        });
    }
    this.form = this.fb.group({
      username: [{ value: '', disabled: !this.admin }, Validators.required],
      nif: [
        { value: '', disabled: !this.admin },
        Validators.compose([
          Validators.required,
          Validators.pattern('[0-9]{8}-[A-Z]{1}'),
        ]),
      ],
      email: [
        { value: '', disabled: !this.admin },
        Validators.compose([Validators.required, Validators.email]),
      ],
      first_name: [
        { value: '', disabled: !this.admin },
        Validators.compose([
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
      ],
      last_name: [
        { value: '', disabled: !this.admin },
        Validators.compose([
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
      ],
      phone_number: [
        { value: '', disabled: !this.admin },
        Validators.compose([
          Validators.required,
          Validators.pattern('[- +()0-9]{15,15}'),
        ]),
      ],
    });

    this.form.setValue({
      username: this.athlete.username,
      nif: this.athlete.nif,
      email: this.athlete.email,
      first_name: this.athlete.first_name,
      last_name: this.athlete.last_name,
      phone_number: this.athlete.phone_number,
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
      enabled: this.athlete.enabled,
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
