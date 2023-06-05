import { Component } from '@angular/core';
import { RegisterTournament } from 'src/app/interfaces/tournament/register-tournament';
import { MatDialog } from '@angular/material/dialog';
import { SportTypeService } from 'src/app/services/sport_type/sport-type.service';
import { SportType } from 'src/app/interfaces/sport.type/SportType';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-register-tournament-organizer',
  templateUrl: './register-tournament-organizer.component.html',
  styleUrls: ['./register-tournament-organizer.component.scss'],
})
export class RegisterTournamentOrganizerComponent {
  sports_types_list: SportType[] = [];

  tournament: RegisterTournament = {
    name: '',
    location: '',
    address: '',
    description: '',
    inscription: false,
    capacity: 0,
    organizer: '',
    sport_type: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private sportTypeService: SportTypeService,
    private tournamentService: TournamentServiceService,
    private AuthService: AuthService,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.sportTypeService.getSportTypes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let sport_type_aux = {
            id: data[i].id,
            name: data[i].name,
            enabled: data[i].enabled,
          };
          this.sports_types_list.push(sport_type_aux);
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay deportes.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        } else {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al cargar los deportes.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        }
      }
    );

    this.form = this.fb.group({
      name: ['', Validators.required],
      location: ['', Validators.required],
      address: ['', Validators.required],
      description: ['', Validators.required],
      capacity: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern('^[0-9]*$'),
        ]),
      ],
      sport_type_name: ['', Validators.required],
    });
  }

  back(): void {
    window.history.back();
  }

  ngOnSubmit(): void {
    this.tournament = {
      name: this.form.value.name,
      location: this.form.value.location,
      address: this.form.value.address,
      description: this.form.value.description,
      inscription: true,
      capacity: this.form.value.capacity,
      organizer: '',
      sport_type: this.form.value.sport_type_name.name,
    };
    this.tournamentService.createTournament(this.tournament).subscribe(
      (response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'El torneo se ha creado correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al crear el torneo.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      }
    );
  }
}
