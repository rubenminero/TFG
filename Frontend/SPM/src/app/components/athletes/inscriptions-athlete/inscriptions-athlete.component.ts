import { Inscriptions } from '../../../interfaces/inscriptions/inscriptions';
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

@Component({
  selector: 'app-inscriptions-athlete',
  templateUrl: './inscriptions-athlete.component.html',
  styleUrls: ['./inscriptions-athlete.component.scss'],
})
export class InscriptionsAthleteComponent {
  inscription_show: boolean = false;
  insctiption: Inscriptions = {
    id: -1,
    tournament: '',
    athlete: '',
    tournament_id: -1,
    athlete_id: -1,
    enabled: false,
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

  inscriptions_saved: Inscriptions[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Inscriptions> =
    new MatTableDataSource<Inscriptions>();

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
            tournament: data[i].tournament,
            athlete: data[i].athlete,
            tournament_id: data[i].tournament_id,
            athlete_id: data[i].athlete_id,
            enabled: data[i].enabled,
          };
          this.inscriptions_saved.push(inscription_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Inscriptions>(
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

  openTournament(inscription: Inscriptions): void {
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

  deleteInscription(inscription: Inscriptions): void {
    this.inscriptionService.deleteInscription(inscription).subscribe(
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
