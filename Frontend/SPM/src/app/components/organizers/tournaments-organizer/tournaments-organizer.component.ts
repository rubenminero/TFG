import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { Component } from '@angular/core';
import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { OrganizerServiceService } from 'src/app/services/organizer/organizer-service.service';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-tournaments-organizer',
  templateUrl: './tournaments-organizer.component.html',
  styleUrls: ['./tournaments-organizer.component.scss'],
})
export class TournamentsOrganizerComponent {
  tournament_show: boolean = false;
  tournament_inscriptions: boolean = false;
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
  };
  tournaments_saved: Tournament[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Tournament> =
    new MatTableDataSource<Tournament>();

  constructor(
    private organizerService: OrganizerServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private tournamentService: TournamentServiceService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.tournament_show = false;
    this.tournament_inscriptions = false;
    this.organizerService.getTournamentsOrganizer().subscribe(
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
            msg: 'Error al cargar los eventos.',
          },
        });
      }
    );
  }

  openTournament(tournament: Tournament): void {
    this.tournament = tournament;
    this.tournament_show = true;
    this.tournament_inscriptions = false;
  }

  watchInscriptions(tournament: Tournament): void {
    this.tournament = tournament;
    this.tournament_show = true;
    this.tournament_inscriptions = true;
  }

  deleteTournament(tournament: Tournament): void {
    this.tournamentService.deleteTournament(tournament).subscribe(
      (response) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Borrado correctamente.',
          },
        });
      },
      (error) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Error al borrar.',
          },
        });
      }
    );
  }
}
