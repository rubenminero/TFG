import { Component } from '@angular/core';
import { EventServiceService } from 'src/app/services/event/event-service.service';
import { Events } from 'src/app/interfaces/event/event';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { ChangeDetectorRef } from '@angular/core';
import { WatchlistService } from 'src/app/services/watchlists/watchlist-service.service';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { EventCardComponent } from '../../shared/models-cards/event-card/event-card.component';
import { RegisterWatchlist } from 'src/app/interfaces/watchlists/register-watchlist';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss'],
})
export class EventsComponent {
  event_show: boolean = false;
  event: Events = {
    id: -1,
    name: '',
    location: '',
    address: '',
    description: '',
    organizer_name: '',
    sport_type_name: '',
  };
  events_saved: Events[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Events> = new MatTableDataSource<Events>();

  constructor(
    private eventService: EventServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private watchlistService: WatchlistService,
    private dialog: MatDialog,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.event_show = false;
    this.eventService.getEvents().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let event_aux = {
            id: data[i].id,
            name: data[i].name,
            location: data[i].location,
            address: data[i].address,
            description: data[i].description,
            organizer_name: data[i].organizer,
            sport_type_name: data[i].sport_type,
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

  saveWatchlist(event: Events): void {
    let watchlist: RegisterWatchlist = {
      tournament: event.id,
      athlete: this.authService.getId(),
    };
    this.watchlistService.addToWatchlist(watchlist).subscribe(
      (response) => {
        console.log(response);
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Añadido correctamente.',
          },
        });
      },
      (error) => {
        if (error.status == 400) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Ya tienes este evento en tu lista.',
            },
          });
        } else if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al añadir.',
            },
          });
        }
      }
    );
  }
}
