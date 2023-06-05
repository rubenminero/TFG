import { Watchlist } from 'src/app/interfaces/watchlists/watchlist';
import { Component } from '@angular/core';
import { Inscriptions } from 'src/app/interfaces/inscriptions/inscriptions';
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
            tournament: data[i].tournament,
            athlete: data[i].athlete,
            tournament_id: data[i].tournament_id,
            athlete_id: data[i].athlete_id,
            enabled: data[i].enabled,
          };
          if (
            inscription_aux.enabled == this.state.value ||
            this.selected.value == ''
          ) {
            this.inscriptions_saved.push(inscription_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Inscriptions>(
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

  changeStateInscription(inscription: Inscriptions): void {
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

  deleteInscription(inscription: Inscriptions): void {
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
