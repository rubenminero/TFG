import { Component } from '@angular/core';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { TournamentsAdminServiceService } from 'src/app/services/admin/tournaments/tournaments-admin-service.service';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-tournaments-admin',
  templateUrl: './tournaments-admin.component.html',
  styleUrls: ['./tournaments-admin.component.scss'],
})
export class TournamentsAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

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
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: TournamentsAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.tournaments_saved = [];
    this.tournament_show = false;
    this.adminService.getTournaments().subscribe(
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
            tournament_aux.enabled == this.state.value ||
            this.selected.value == ''
          ) {
            this.tournaments_saved.push(tournament_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Tournament>(
          this.tournaments_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.tournaments_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay torneos ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay torneos.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los torneos.',
            },
          });
        }
      }
    );
  }

  openTournament(tournament: Tournament): void {
    this.tournament = tournament;
    this.tournament_show = true;
  }

  changeStateTournament(tournament: Tournament): void {
    this.adminService
      .changeStateTournament(tournament)
      .subscribe((response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Estado cambiado correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      });
  }

  deleteTournament(tournament: Tournament): void {
    this.adminService.deleteTournament(tournament).subscribe(
      (response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Borrado correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
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
