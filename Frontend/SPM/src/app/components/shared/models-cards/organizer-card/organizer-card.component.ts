import { Component } from '@angular/core';
import { Organizer } from 'src/app/interfaces/organizer/organizer';
import { OrganizerServiceService } from 'src/app/services/organizer/organizer-service.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpPasswordComponent } from '../../pop-ups/pop-up-password/pop-up-password.component';
import { PopUpMsgComponent } from '../../pop-ups/pop-up-msg/pop-up-msg.component';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-organizer-card',
  templateUrl: './organizer-card.component.html',
  styleUrls: ['./organizer-card.component.scss'],
})
export class OrganizerCardComponent {
  organizer: Organizer = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    company: '',
    address: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private dialog: MatDialog,
    private organizerService: OrganizerServiceService,
    private AuthService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.organizerService.getOrganizer(this.AuthService.getId()).subscribe(
      (response) => {
        this.organizer = {
          id: response.id,
          username: response.username,
          nif: response.nif,
          email: response.email,
          first_name: response.first_name,
          last_name: response.last_name,
          company: response.company,
          address: response.address,
        };

        this.form.setValue({
          username: this.organizer.username,
          nif: this.organizer.nif,
          email: this.organizer.email,
          first_name: this.organizer.first_name,
          last_name: this.organizer.last_name,
          company: this.organizer.company,
          address: this.organizer.address,
        });
      },
      (error) => {
        this.AuthService.getPath();
      }
    );
    this.form = this.fb.group({
      username: new FormControl('', [Validators.required]),
      nif: new FormControl('', [
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
      company: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
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

  get company() {
    return this.form.get('company');
  }

  get address() {
    return this.form.get('address');
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
    this.organizerService.deleteOrganizer(this.organizer.id).subscribe(
      (response) => {
        this.logout();
        this.back();
      },
      (error) => {
        console.log('Organizer: ' + error);
      }
    );
  }

  ngOnSubmit(): void {
    this.organizer = {
      id: this.organizer.id,
      username: this.form.value.username,
      nif: this.form.value.nif,
      email: this.form.value.email,
      first_name: this.form.value.first_name,
      last_name: this.form.value.last_name,
      company: this.form.value.company,
      address: this.form.value.address,
    };

    this.organizerService.updateOrganizer(this.organizer).subscribe(
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
