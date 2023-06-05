import { Component } from '@angular/core';
import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Events } from 'src/app/interfaces/event/event';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EventsAdminServiceService } from 'src/app/services/admin/events/events-admin-service.service';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-events-admin',
  templateUrl: './events-admin.component.html',
  styleUrls: ['./events-admin.component.scss'],
})
export class EventsAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

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
  events_saved: Events[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Events> = new MatTableDataSource<Events>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: EventsAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.events_saved = [];
    this.event_show = false;
    this.adminService.getEvents().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let event_aux = {
            id: data[i].id,
            name: data[i].name,
            location: data[i].location,
            address: data[i].address,
            description: data[i].description,
            organizer: data[i].organizer,
            sport_type: data[i].sport_type,
            enabled: data[i].enabled,
          };
          if (
            this.state.value == event_aux.enabled ||
            this.selected.value == ''
          ) {
            this.events_saved.push(event_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Events>(this.events_saved);
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.events_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay eventos ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay eventos.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los eventos.',
            },
          });
        }
      }
    );
  }

  openEvent(event: Events): void {
    this.event = this.event;
    this.event_show = true;
  }

  changeStateEvent(event: Events): void {
    this.adminService.changeStateEvent(event).subscribe((response) => {
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

  deleteEvent(event: Events): void {
    this.adminService.deleteEvent(event).subscribe(
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
