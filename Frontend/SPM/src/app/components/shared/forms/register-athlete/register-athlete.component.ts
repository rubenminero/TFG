import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AthleteServiceService } from 'src/app/services/athlete/athlete-service.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { RegisterAthlete } from 'src/app/interfaces/athelete/RegisterAthlete';
import { MatDialog } from '@angular/material/dialog';
import { PopUpRegisterComponent } from '../../pop-ups/pop-up-register/pop-up-register.component';

@Component({
  selector: 'app-register-athlete',
  templateUrl: './register-athlete.component.html',
  styleUrls: ['./register-athlete.component.scss'],
})
export class RegisterAthleteComponent {
  athlete: RegisterAthlete = {
    username: '',
    password: '',
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
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    let athelete_path = this.athleteService.getAthletePath();
    if (athelete_path === '/athletes-menu') {
      this.router.navigate(['/athletes-menu']);
    } else if (athelete_path === '/organizers-menu') {
      this.router.navigate(['/organizers-menu']);
    } else if (athelete_path === '/admins-menu') {
      this.router.navigate(['/admins-menu']);
    } else {
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
        phone_number: new FormControl('', [
          Validators.pattern('[- +()0-9]{15,15}'),
          Validators.required,
        ]),
      });
    }
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

  get phone_number() {
    return this.form.get('phone_number');
  }

  clearForm() {
    this.form.reset();
  }

  ngOnSubmit(): void {
    if (this.form.valid) {
      if (this.form.value.password != this.form.value.passwordConfirm) {
        this.dialog.open(PopUpRegisterComponent, {
          data: {
            register: false,
            msg: 'Las contraseñas no coinciden.',
          },
        });
      } else {
        this.athlete = {
          username: this.form.value.username,
          password: this.form.value.password,
          nif: this.form.value.nif,
          email: this.form.value.email,
          first_name: this.form.value.first_name,
          last_name: this.form.value.first_name,
          phone_number: this.form.value.phone_number,
        };
        this.athleteService.registerAthlete(this.athlete).subscribe(
          (response) => {
            if (response.status == 500) {
              this.dialog.open(PopUpRegisterComponent, {
                data: {
                  register: false,
                  msg: 'Error en el registro.',
                },
              });
            }
          },
          (error) => {
            if (error.status == 403) {
              this.dialog.open(PopUpRegisterComponent, {
                data: {
                  register: false,
                  msg: 'El nombre de usuario ya existe.',
                },
              });
            }
          },
          () => {
            this.dialog.open(PopUpRegisterComponent, {
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
