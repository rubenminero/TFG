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
  };
  events_saved: Events[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Events> = new MatTableDataSource<Events>();

  constructor(
    private organizerService: OrganizerServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private eventService: EventServiceService,
    private dialog: MatDialog
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
          };
          this.events_saved.push(event_aux);
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Events>(this.events_saved);
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

  openEvent(event: Events): void {
    this.event = event;
    this.event_show = true;
  }

  deleteEvent(event: Events): void {
    this.eventService.deleteEvent(event).subscribe(
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
