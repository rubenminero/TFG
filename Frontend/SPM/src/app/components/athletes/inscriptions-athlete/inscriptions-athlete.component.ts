import { Component } from '@angular/core';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { InscriptionServiceService } from 'src/app/services/inscriptions/inscription-service.service';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { WatchlistService } from 'src/app/services/watchlists/watchlist-service.service';
import { ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { InscriptionsTournament } from 'src/app/interfaces/inscriptions/inscriptions-tournament';

@Component({
  selector: 'app-inscriptions-athlete',
  templateUrl: './inscriptions-athlete.component.html',
  styleUrls: ['./inscriptions-athlete.component.scss'],
})
export class InscriptionsAthleteComponent {
  inscription_show: boolean = false;
  inscription: InscriptionsTournament = {
    id: -1,
    athlete: '',
    athlete_id: -1,
    enabled: false,
    tournament_id: -1,
    tournament_name: '',
    tournament_location: '',
    tournament_address: '',
    tournament_description: '',
    tournament_inscription: false,
    tournament_capacity: -1,
    tournament_enabled: false,
    tournament_organizer: '',
    tournament_sport_type: '',
  };
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

  inscriptions_saved: InscriptionsTournament[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<InscriptionsTournament> =
    new MatTableDataSource<InscriptionsTournament>();

  constructor(
    private inscriptionService: InscriptionServiceService,
    private tournamentService: TournamentServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private watchlistService: WatchlistService,
    private dialog: MatDialog,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.inscription_show = false;
    this.inscriptionService.getInscriptions().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let inscription_aux = {
            id: data[i].id,
            athlete: data[i].athlete,
            athlete_id: data[i].athlete_id,
            enabled: data[i].enabled,
            tournament_id: data[i].tournament_id,
            tournament_name: data[i].tournament_name,
            tournament_location: data[i].tournament_location,
            tournament_address: data[i].tournament_address,
            tournament_description: data[i].tournament_description,
            tournament_inscription: data[i].tournament_inscription,
            tournament_capacity: data[i].tournament_capacity,
            tournament_enabled: data[i].tournament_enabled,
            tournament_organizer: data[i].tournament_organizer,
            tournament_sport_type: data[i].tournament_sport_type,
          };
          this.inscriptions_saved.push(inscription_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<InscriptionsTournament>(
          this.inscriptions_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No tienes inscripciones.',
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
                msg: 'Error al cargar las inscripciones.',
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

  openTournament(inscription: InscriptionsTournament): void {
    this.tournamentService.getTournament(inscription.tournament_id).subscribe(
      (response) => {
        this.tournament = {
          id: response.id,
          name: response.name,
          location: response.location,
          address: response.address,
          description: response.description,
          inscription: response.inscription,
          capacity: response.capacity,
          organizer: response.organizer,
          sport_type: response.sport_type,
          enabled: response.enabled,
        };
        this.inscription_show = true;
      },
      (error) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar el torneo.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      }
    );
  }

  deleteInscription(inscription: InscriptionsTournament): void {
    this.inscriptionService.deleteInscription(inscription.id).subscribe(
      (response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Borrada correctamente.',
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
                msg: 'La inscripcion no pertenece a tu cuenta.',
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
