import { Component } from '@angular/core';
import { Watchlist } from 'src/app/interfaces/watchlists/watchlist';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { Observable } from 'rxjs';
import { WatchlistService } from 'src/app/services/watchlists/watchlist-service.service';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { Events } from 'src/app/interfaces/event/event';
import { ViewChild } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-watchlist-athlete',
  templateUrl: './watchlist-athlete.component.html',
  styleUrls: ['./watchlist-athlete.component.scss'],
})
export class WatchlistAthleteComponent {
  watchlist_show: boolean = false;
  watchlist: Watchlist = {
    id: -1,
    athlete_name: '',
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
  event_show: boolean = false;
  event: Events = {
    id: -1,
    name: '',
    location: '',
    address: '',
    description: '',
    organizer: '',
    sport_type: '',
    enabled: false,
  };

  watchlists_saved: Watchlist[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Watchlist> =
    new MatTableDataSource<Watchlist>();

  constructor(
    private watchlistService: WatchlistService,
    private tournamentService: TournamentServiceService,
    private authService: AuthService,
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.watchlist_show = false;
    this.watchlists_saved = [];
    this.watchlistService.getWatchlists().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let watchlist_aux = {
            id: data[i].id,
            athlete_name: data[i].athlete,
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
          this.watchlists_saved.push(watchlist_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Watchlist>(
          this.watchlists_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Tu lista de seguimiento esta vacia.',
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
                msg: 'Error al cargar tu lista de seguimiento.',
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

  openTournament(watchlist: Watchlist): void {
    this.tournamentService.getTournament(watchlist.tournament_id).subscribe(
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

        if (this.tournament.inscription) {
          this.tournament_show = true;
          this.event = {
            id: -1,
            name: '',
            location: '',
            address: '',
            description: '',
            organizer: '',
            sport_type: '',
            enabled: false,
          };
          this.event_show = false;
        } else {
          this.tournament_show = false;
          this.event = {
            id: this.tournament.id,
            name: this.tournament.name,
            location: this.tournament.location,
            address: this.tournament.address,
            description: this.tournament.description,
            organizer: this.tournament.organizer,
            sport_type: this.tournament.sport_type,
            enabled: this.tournament.enabled,
          };
          this.tournament = {
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
          this.event_show = true;
        }
        this.watchlist_show = true;
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

  deleteWatchlist(watchlist: Watchlist): void {
    this.watchlistService.deleteWatchlist(watchlist).subscribe(
      (response) => {
        if (response.status == 200) {
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
        }
      },
      (error) => {
        if (error.status == 200) {
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
        } else if (error.status == 400) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Este torneo o evento no esta en tu lista de seguimiento.',
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
                msg: 'Error al añadirXD.',
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
