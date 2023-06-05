import { AuthService } from 'src/app/services/auth/auth-service.service';
import { Component } from '@angular/core';
import { OrganizerServiceService } from 'src/app/services/organizer/organizer-service.service';
import { ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { Router } from '@angular/router';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { Events } from 'src/app/interfaces/event/event';
import { EventServiceService } from 'src/app/services/event/event-service.service';

@Component({
  selector: 'app-events-organizer',
  templateUrl: './events-organizer.component.html',
  styleUrls: ['./events-organizer.component.scss'],
})
export class EventsOrganizerComponent {
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
    private organizerService: OrganizerServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private eventService: EventServiceService,
    private dialog: MatDialog,
    private AuthService: AuthService
  ) {}

  ngOnInit(): void {
    this.event_show = false;
    this.organizerService.getEventsOrganizer().subscribe(
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
          this.events_saved.push(event_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Events>(this.events_saved);
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.events_saved.length == 0) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No tienes eventos.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay eventos.',
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
                msg: 'Error al cargar los eventos.',
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

  openEvent(event: Events): void {
    this.event = event;
    this.event_show = true;
  }

  deleteEvent(event: Events): void {
    this.eventService.deleteEvent(event).subscribe(
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
              msg: 'Error al borrar.',
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
