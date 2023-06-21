import { Component } from '@angular/core';
import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { InscriptionsAdminServiceService } from 'src/app/services/admin/inscriptions/inscriptions-admin-service.service';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';
import { InscriptionsTournament } from 'src/app/interfaces/inscriptions/inscriptions-tournament';

@Component({
  selector: 'app-inscriptions-admin',
  templateUrl: './inscriptions-admin.component.html',
  styleUrls: ['./inscriptions-admin.component.scss'],
})
export class InscriptionsAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

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
  inscriptions_saved: InscriptionsTournament[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<InscriptionsTournament> =
    new MatTableDataSource<InscriptionsTournament>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: InscriptionsAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.inscriptions_saved = [];
    this.adminService.getInscriptions().subscribe(
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
          if (
            inscription_aux.enabled == this.state.value ||
            this.selected.value == ''
          ) {
            this.inscriptions_saved.push(inscription_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<InscriptionsTournament>(
          this.inscriptions_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.inscriptions_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay inscripciones ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay inscripciones.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar las inscripciones.',
            },
          });
        }
      }
    );
  }

  changeStateInscription(inscription: InscriptionsTournament): void {
    this.adminService
      .changeStateInscription(inscription)
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

  deleteInscription(inscription: InscriptionsTournament): void {
    this.adminService.deleteInscription(inscription).subscribe(
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
