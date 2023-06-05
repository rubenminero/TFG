import { shareReplay } from 'rxjs/operators';
import { SportTypeService } from 'src/app/services/sport_type/sport-type.service';
import { Component } from '@angular/core';
import { SportType } from 'src/app/interfaces/sport.type/SportType';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { Observable } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SportsTypesAdminServiceService } from 'src/app/services/admin/sports-types/sport-types-admin.service';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-sports-types-admin',
  templateUrl: './sports-types-admin.component.html',
  styleUrls: ['./sports-types-admin.component.scss'],
})
export class SportsTypesAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

  sport_type: SportType = {
    id: -1,
    name: '',
    enabled: false,
  };

  sport_types_saved: SportType[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<SportType> =
    new MatTableDataSource<SportType>();

  constructor(
    private adminService: SportsTypesAdminServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private authService: AuthService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.sport_types_saved = [];
    this.adminService.getAllSportsTypes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let sport_type_aux = {
            id: data[i].id,
            name: data[i].name,
            enabled: data[i].enabled,
          };
          if (
            this.state.value == sport_type_aux.enabled ||
            this.selected.value == ''
          ) {
            this.sport_types_saved.push(sport_type_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<SportType>(
          this.sport_types_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.sport_types_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay deportes ' + txt + '.',
            },
          });
          this.authService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay deportes.',
            },
          });
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los deportes.',
            },
          });
        }
      }
    );
  }

  changeStateSportType(sportType: SportType): void {
    this.adminService.changeStateSportType(sportType).subscribe(
      (response) => {
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
      },
      (error) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cambiar el estado.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      }
    );
  }

  deleteSportType(sportType: SportType): void {
    this.adminService.deleteSportType(sportType).subscribe(
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
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cambiar el estado.',
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
