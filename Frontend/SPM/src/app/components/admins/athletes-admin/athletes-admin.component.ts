import { Component } from '@angular/core';
import { Athlete } from 'src/app/interfaces/athlete/Athlete';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { AthletesAdminServiceService } from './../../../services/admin/athletes/athletes-admin-service.service';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { MatDialog } from '@angular/material/dialog';
import { ChangeDetectorRef } from '@angular/core';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-athletes-admin',
  templateUrl: './athletes-admin.component.html',
  styleUrls: ['./athletes-admin.component.scss'],
})
export class AthletesAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

  athlete_show: boolean = false;
  athlete: Athlete = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    phone_number: '',
    enabled: false,
  };
  athletes_saved: Athlete[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Athlete> = new MatTableDataSource<Athlete>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: AthletesAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.athletes_saved = [];
    this.athlete_show = false;
    this.adminService.getAthletes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let athlete_aux = {
            id: data[i].id,
            username: data[i].username,
            nif: data[i].nif,
            email: data[i].email,
            first_name: data[i].first_name,
            last_name: data[i].last_name,
            phone_number: data[i].phone_number,
            enabled: data[i].enabled,
          };
          if (
            this.state.value == athlete_aux.enabled ||
            this.selected.value == ''
          ) {
            this.athletes_saved.push(athlete_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Athlete>(this.athletes_saved);
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.athletes_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay atletas ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay atletas.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los atletas.',
            },
          });
        }
      }
    );
  }

  openAthlete(athlete: Athlete): void {
    this.athlete = athlete;
    this.athlete_show = true;
  }

  changeStateAthlete(athlete: Athlete): void {
    this.adminService.changeStateAthlete(athlete).subscribe((response) => {
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

  deleteAthlete(athlete: Athlete): void {
    this.adminService.deleteAthlete(athlete).subscribe(
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
