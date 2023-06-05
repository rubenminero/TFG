import { Component } from '@angular/core';
import { Organizer } from 'src/app/interfaces/organizer/organizer';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { OrganizersAdminServiceService } from './../../../services/admin/organizers/organizers-admin-service.service';
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
  selector: 'app-organizers-admin',
  templateUrl: './organizers-admin.component.html',
  styleUrls: ['./organizers-admin.component.scss'],
})
export class OrganizersAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

  organizer_show: boolean = false;
  organizer: Organizer = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    company: '',
    address: '',
    enabled: false,
  };
  organizers_saved: Organizer[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Organizer> =
    new MatTableDataSource<Organizer>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: OrganizersAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.organizers_saved = [];
    this.organizer_show = false;
    this.adminService.getOrganizers().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let organizer_aux = {
            id: data[i].id,
            username: data[i].username,
            nif: data[i].nif,
            email: data[i].email,
            first_name: data[i].first_name,
            last_name: data[i].last_name,
            company: data[i].company,
            address: data[i].address,
            enabled: data[i].enabled,
          };
          if (
            organizer_aux.enabled == this.state.value ||
            this.selected.value == ''
          ) {
            this.organizers_saved.push(organizer_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Organizer>(
          this.organizers_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.organizers_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay organizadores ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay organizadores.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los organizadores.',
            },
          });
        }
      }
    );
  }

  openOrganizer(organizer: Organizer): void {
    this.organizer = organizer;
    this.organizer_show = true;
  }

  changeStateOrganizer(organizer: Organizer): void {
    this.adminService.changeStateOrganizer(organizer).subscribe((response) => {
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

  deleteOrganizer(organizer: Organizer): void {
    this.adminService.deleteOrganizer(organizer).subscribe(
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
