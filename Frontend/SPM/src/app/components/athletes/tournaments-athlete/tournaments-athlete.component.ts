import { WatchlistService } from 'src/app/services/watchlists/watchlist-service.service';
import { AuthService } from '../../../services/auth/auth-service.service';
import { Tournament } from '../../../interfaces/tournament/tournament';
import { Component } from '@angular/core';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { ChangeDetectorRef } from '@angular/core';
import { InscriptionServiceService } from 'src/app/services/inscriptions/inscription-service.service';
import { RegisterInscription } from 'src/app/interfaces/inscriptions/register-inscription';
import { RegisterWatchlist } from 'src/app/interfaces/watchlists/register-watchlist';
import { FormControl } from '@angular/forms';
import { SportType } from 'src/app/interfaces/sport.type/SportType';
import { SportTypeService } from 'src/app/services/sport_type/sport-type.service';

@Component({
  selector: 'app-tournaments-athlete',
  templateUrl: './tournaments-athlete.component.html',
  styleUrls: ['./tournaments-athlete.component.scss'],
})
export class TournamentsAthleteComponent {
  sports_types: SportType[] = [];
  sport_type: SportType = {
    id: -1,
    name: '',
    enabled: false,
  };
  location: string = '';
  select_sport_type = new FormControl('');
  select_location = new FormControl('');

  tournament_show: boolean = false;
  tournament: Tournament = {
    id: -1,
    name: '',
    location: '',
    address: '',
    description: '',
    inscription: false,
    capacity: -1,
    organizer: '',
    sport_type: '',
    enabled: false,
  };
  tournaments_saved: Tournament[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Tournament> =
    new MatTableDataSource<Tournament>();

  constructor(
    private tournamentService: TournamentServiceService,
    private inscriptionService: InscriptionServiceService,
    private watchlistService: WatchlistService,
    private sportTypeService: SportTypeService,
    private authService: AuthService,
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog
  ) {}

  selectSportType(sport_type: SportType): void {
    this.sport_type.id = sport_type.id;
    this.sport_type.name = sport_type.name;
    this.sport_type.enabled = sport_type.enabled;
    this.selectLocation();
  }

  selectLocation(): void {
    if (this.select_location.value != null) {
      this.location = this.select_location.value;
    } else {
      this.location = '';
    }
    this.loadTournaments();
  }

  ngOnInit(): void {
    this.tournament_show = false;
    this.sportTypeService.getSportTypes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let sport_type_aux = {
            id: data[i].id,
            name: data[i].name,
            enabled: data[i].enabled,
          };
          this.sports_types.push(sport_type_aux);
        }
        this.loadTournaments();
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay tipos de deportes.',
            },
          });
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los tipos de deportes.',
            },
          });
        }
        this.authService.getPath();
      }
    );
  }

  loadTournaments(): void {
    this.tournaments_saved = [];
    this.tournamentService.getTournaments().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let tournament_aux = {
            id: data[i].id,
            name: data[i].name,
            location: data[i].location,
            address: data[i].address,
            description: data[i].description,
            inscription: data[i].inscription,
            capacity: data[i].capacity,
            organizer: data[i].organizer,
            sport_type: data[i].sport_type,
            enabled: data[i].enabled,
          };
          if (
            (tournament_aux.sport_type == this.sport_type.name ||
              this.sport_type.id == -1) &&
            (tournament_aux.location == this.location || this.location == '')
          ) {
            this.tournaments_saved.push(tournament_aux);
          }
        }
        if (this.tournaments_saved.length == 0) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay torneos que cumplan los filtros.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.authService.getPath();
            });
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Tournament>(
          this.tournaments_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay torneos.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.authService.getPath();
            });
        } else {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al cargar las torneos.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.authService.getPath();
            });
        }
      }
    );
  }

  openTournament(tournament: Tournament): void {
    this.tournament = tournament;
    this.tournament_show = true;
  }

  saveInscription(tournament: Tournament): void {
    let inscription: RegisterInscription = {
      tournament: tournament.name,
      tournament_id: tournament.id,
      athlete: this.authService.getSubject(),
      athlete_id: this.authService.getId(),
    };
    this.inscriptionService.addTournamentToInscription(inscription).subscribe(
      (response) => {
        console.log(response);
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Añadido correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        if (error.status == 400) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Ya estas inscrito en este torneo.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        } else if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al añadir.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        }
      }
    );
  }

  saveWatchlist(tournament: Tournament): void {
    let Watchlist: RegisterWatchlist = {
      tournament: tournament.id,
      athlete: this.authService.getId(),
    };
    this.watchlistService.addToWatchlist(Watchlist).subscribe(
      (response) => {
        console.log(response);
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Añadido correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        if (error.status == 400) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Ya tienes este torneo en tu lista.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        } else if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al añadir.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        }
      }
    );
  }
}
