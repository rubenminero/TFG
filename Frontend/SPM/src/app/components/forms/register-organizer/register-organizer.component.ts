import { Component } from '@angular/core';
import { RegisterOrganizer } from 'src/app/interfaces/organizer/register-organizer';
import { OrganizerServiceService } from 'src/app/services/organizer/organizer-service.service';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-register-organizer',
  templateUrl: './register-organizer.component.html',
  styleUrls: ['./register-organizer.component.scss'],
})
export class RegisterOrganizerComponent {
  organizer: RegisterOrganizer = {
    username: '',
    password: '',
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
    private router: Router,
    private organizerService: OrganizerServiceService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    let organizer_path = this.organizerService.getOrganizerPath();
    if (organizer_path === '/home-organizer') {
      this.router.navigate(['/home-organizer']);
    } else if (organizer_path === '/home-athlete') {
      this.router.navigate(['/home-athlete']);
    } else if (organizer_path === '/home-admin') {
      this.router.navigate(['/home-admin']);
    } else {
<<<<<<< Updated upstream:Frontend/SPM/src/app/components/forms/register-organizer/register-organizer.component.ts
      this.router.navigate(['/role-error']);
=======
      this.form = this.fb.group({
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        passwordConfirm: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
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
>>>>>>> Stashed changes:Frontend/SPM/src/app/components/shared/forms/register-organizer/register-organizer.component.ts
    }
    this.form = this.fb.group({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
      ]),
      passwordConfirm: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
      ]),
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
      company_name: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
    });
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  get passwordConfirm() {
    return this.form.get('passwordConfirm');
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

  clearForm() {
    this.form.reset();
  }

  ngOnSubmit(): void {
    if (this.form.valid) {
      if (this.form.value.password != this.form.value.passwordConfirm) {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Las contraseñas no coinciden.',
          },
        });
      } else {
        this.organizer = {
          username: this.form.value.username,
          password: this.form.value.password,
          nif: this.form.value.nif,
          email: this.form.value.email,
          first_name: this.form.value.first_name,
          last_name: this.form.value.first_name,
          company: this.form.value.company,
          address: this.form.value.address,
        };
        this.organizerService.registerOrganizer(this.organizer).subscribe(
          (response) => {
            if (response.status == 500) {
              this.dialog.open(PopUpMsgComponent, {
                data: {
                  register: false,
                  msg: 'Error en el registro.',
                },
              });
            }
          },
          (error) => {
            if (error.status == 403) {
              this.dialog.open(PopUpMsgComponent, {
                data: {
                  register: false,
                  msg: 'El nombre de usuario ya existe.',
                },
              });
            }
          },
          () => {
            this.dialog.open(PopUpMsgComponent, {
              data: {
                register: true,
                msg: 'Te has registrado correctamente.',
              },
            });
          }
        );
      }
    }
  }
}
