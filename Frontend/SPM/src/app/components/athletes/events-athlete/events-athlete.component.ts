import { SportsTypesAdminServiceService } from 'src/app/services/admin/sports-types/sport-types-admin.service';
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
import { RegisterWatchlist } from 'src/app/interfaces/watchlists/register-watchlist';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { SportType } from 'src/app/interfaces/sport.type/SportType';
import { SportTypeService } from 'src/app/services/sport_type/sport-type.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-events-athlete',
  templateUrl: './events-athlete.component.html',
  styleUrls: ['./events-athlete.component.scss'],
})
export class EventsAthleteComponent {
  sports_types: SportType[] = [];
  sport_type: SportType = {
    id: -1,
    name: '',
    enabled: false,
  };
  location: string = '';
  select_sport_type = new FormControl('');
  select_location = new FormControl('');

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
    private eventService: EventServiceService,
    private changeDetectorRef: ChangeDetectorRef,
    private watchlistService: WatchlistService,
    private sportTypeService: SportTypeService,
    private dialog: MatDialog,
    private authService: AuthService
  ) {}

  selectSportType(sport_type: SportType): void {
    this.sport_type.id = sport_type.id;
    this.sport_type.name = sport_type.name;
    this.sport_type.enabled = sport_type.enabled;
    this.loadEvents();
  }

  selectLocation(): void {
    console.log('ADAD' + this.select_location.value);
    if (this.select_location.value != null) {
      this.location = this.select_location.value;
    } else {
      this.location = '';
    }
    this.loadEvents();
  }

  ngOnInit(): void {
    this.event_show = false;
    this.sportTypeService.getSportTypes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let sport_type_aux = {
            id: data[i].id,
            name: data[i].name,
            enabled: data[i].enabled,
          };
          this.sports_types.push(sport_type_aux);
        }
        this.loadEvents();
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay tipos de deportes.',
            },
          });
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los tipos de deportes.',
            },
          });
        }
        this.authService.getPath();
      }
    );
  }

  loadEvents(): void {
    this.events_saved = [];
    this.eventService.getEvents().subscribe(
      (data: any[]) => {
        console.log(data);
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
            (event_aux.sport_type == this.sport_type.name ||
              this.sport_type.id == -1) &&
            (event_aux.location == this.location || this.location == '')
          ) {
            this.events_saved.push(event_aux);
          }
        }
        if (this.events_saved.length == 0) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay eventos que cumplan los filtros.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.authService.getPath();
            });
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Events>(this.events_saved);
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
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
              this.authService.getPath();
            });
        } else {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al cargar las eventos.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.authService.getPath();
            });
        }
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
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Añadido correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        if (error.status == 400) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Ya tienes este evento en tu lista.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        } else if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al añadir.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.ngOnInit();
            });
        }
      }
    );
  }
}
