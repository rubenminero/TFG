import { WatchlistService } from 'src/app/services/watchlists/watchlist-service.service';
import { AuthService } from './../../../services/auth/auth-service.service';
import { Tournament } from './../../../interfaces/tournament/tournament';
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

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.scss'],
})
export class TournamentsComponent {
  tournament_show: boolean = false;
  tournament: Tournament = {
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
  tournaments_saved: Tournament[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Tournament> =
    new MatTableDataSource<Tournament>();

  constructor(
    private tournamentService: TournamentServiceService,
    private inscriptionService: InscriptionServiceService,
    private watchlistService: WatchlistService,
    private AuthService: AuthService,
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.tournament_show = false;
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
            organizer_name: data[i].organizer,
            sport_type_name: data[i].sport_type,
          };
          this.tournaments_saved.push(tournament_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Tournament>(
          this.tournaments_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
      },
      (error) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Error al cargar los torneos.',
          },
        });
      }
    );
  }

  openTournament(tournament: Tournament): void {
    this.tournament = tournament;
    this.tournament_show = true;
  }

  saveInscription(tournament: Tournament): void {
    let inscription: RegisterInscription = {
      tournament: tournament.id,
      athlete: this.AuthService.getId(),
    };
    this.inscriptionService.addTournamentToInscription(inscription).subscribe(
      (response) => {
        console.log(response);
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Añadido correctamente.',
          },
        });
      },
      (error) => {
        console.log(error);
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Error al añadir.',
          },
        });
      }
    );
  }

  saveWatchlist(tournament: Tournament): void {
    let Watchlist: RegisterWatchlist = {
      tournament: tournament.id,
      athlete: this.AuthService.getId(),
    };
    this.watchlistService.addToWatchlist(Watchlist).subscribe(
      (response) => {
        console.log(response);
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Añadido correctamente.',
          },
        });
      },
      (error) => {
        console.log(error);
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Error al añadir.',
          },
        });
      }
    );
  }
}
