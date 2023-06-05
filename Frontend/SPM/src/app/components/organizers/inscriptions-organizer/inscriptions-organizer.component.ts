import { Component } from '@angular/core';
import { Inscriptions } from 'src/app/interfaces/inscriptions/inscriptions';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { TournamentServiceService } from 'src/app/services/tournament/tournament-service.service';
import { InscriptionServiceService } from 'src/app/services/inscriptions/inscription-service.service';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ChangeDetectorRef, Input, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-inscriptions-organizer',
  templateUrl: './inscriptions-organizer.component.html',
  styleUrls: ['./inscriptions-organizer.component.scss'],
})
export class InscriptionsOrganizerComponent {
  @Input() tournament: Tournament = {
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
  inscription: Inscriptions = {
    id: -1,
    tournament: '',
    athlete: '',
    tournament_id: -1,
    athlete_id: -1,
    enabled: false,
  };
  inscriptions_saved: Inscriptions[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Inscriptions> =
    new MatTableDataSource<Inscriptions>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private tournamentService: TournamentServiceService,
    private inscriptionService: InscriptionServiceService,
    private AuthService: AuthService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.tournamentService.getInscriptionsTournament(this.tournament).subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let inscriptions_aux = {
            id: data[i].id,
            tournament: data[i].tournament,
            athlete: data[i].athlete,
            tournament_id: data[i].tournament_id,
            athlete_id: data[i].athlete_id,
            enabled: data[i].enabled,
          };
          this.inscriptions_saved.push(inscriptions_aux);
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
                msg: 'No hay inscripciones para este torneo.',
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
                msg: 'Error al cargar las inscripciones.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        }
      }
    );
  }

  deleteInscription(inscription: Inscriptions): void {
    this.inscriptionService.deleteInscription(inscription).subscribe(
      (response) => {
        console.log(response);
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Inscripción eliminada correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        console.log(error);
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al eliminar la inscripción.',
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
