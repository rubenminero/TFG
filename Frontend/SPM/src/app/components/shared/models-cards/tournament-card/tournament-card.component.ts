import { Component, Input } from '@angular/core';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { Router } from '@angular/router';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpRegisterComponent } from '../../pop-ups/pop-up-register/pop-up-register.component';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { PopUpMsgComponent } from '../../pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-tournament-card',
  templateUrl: './tournament-card.component.html',
  styleUrls: ['./tournament-card.component.scss'],
})
export class TournamentCardComponent {
  @Input() admin: boolean = false;
  @Input() tournament: Tournament = {
    id: -1,
    name: '',
    location: '',
    address: '',
    description: '',
    inscription: false,
    capacity: -1,
    organizer_name: '',
    sport_type_name: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private tournamentService: TournamentServiceService,
    private AuthService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    if (this.tournament.id == -1) {
      this.dialog.open(PopUpMsgComponent, {
        data: {
          msg: 'Error al intentar mostrar los datos del torneo.',
        },
      });
    }
    this.form = this.fb.group({
      name: [{ value: '', disabled: !this.admin }, Validators.required],
      location: [{ value: '', disabled: !this.admin }, Validators.required],
      address: [{ value: '', disabled: !this.admin }, Validators.required],
      description: [{ value: '', disabled: !this.admin }, Validators.required],
      inscription: [{ value: '', disabled: !this.admin }, Validators.required],
      capacity: [{ value: '', disabled: !this.admin }, Validators.required],
      organizer_name: [
        { value: '', disabled: !this.admin },
        Validators.required,
      ],
      sport_type_name: [
        { value: '', disabled: !this.admin },
        Validators.required,
      ],
    });

    this.form.setValue({
      name: this.tournament.name,
      location: this.tournament.location,
      address: this.tournament.address,
      description: this.tournament.description,
      inscription: this.tournament.inscription,
      capacity: this.tournament.capacity,
      organizer_name: this.tournament.organizer_name,
      sport_type_name: this.tournament.sport_type_name,
    });
  }

  get name() {
    return this.form.get('name');
  }

  get location() {
    return this.form.get('location');
  }

  get address() {
    return this.form.get('address');
  }

  get description() {
    return this.form.get('description');
  }

  get inscription() {
    return this.form.get('inscription');
  }

  get capacity() {
    return this.form.get('capacity');
  }

  get organizer_name() {
    return this.form.get('organizer_name');
  }

  get sport_type_name() {
    return this.form.get('sport_type_name');
  }

  back(): void {
    window.history.back();
  }

  ngOnSubmit(): void {
    this.tournament = {
      id: this.form.value.id,
      name: this.form.value.name,
      location: this.form.value.location,
      address: this.form.value.address,
      description: this.form.value.description,
      inscription: this.form.value.inscription,
      capacity: this.form.value.capacity,
      organizer_name: this.form.value.organizer_name,
      sport_type_name: this.form.value.sport_type_name,
    };
    this.tournamentService.updateTournament(this.tournament).subscribe(
      (response) => {
        if (response.status == 500) {
          this.dialog.open(PopUpRegisterComponent, {
            data: {
              register: false,
              msg: 'Error al actualizar.',
            },
          });
        }
      },
      (error) => {
        console.log(error);
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
            msg: 'Has actualizado tus datos  correctamente.',
          },
        });
      }
    );
  }
}
