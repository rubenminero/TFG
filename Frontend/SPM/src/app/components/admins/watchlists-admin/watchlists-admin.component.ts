import { Component } from '@angular/core';
import { Watchlist } from 'src/app/interfaces/watchlists/watchlist';
import { ChangeDetectorRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { WatchlistsAdminServiceService } from 'src/app/services/admin/watchlists/watchlists-admin-service.service';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';
import { State } from 'src/app/interfaces/state';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-watchlists-admin',
  templateUrl: './watchlists-admin.component.html',
  styleUrls: ['./watchlists-admin.component.scss'],
})
export class WatchlistsAdminComponent {
  state: State = {
    value: true,
    viewValue: '',
  };
  states: State[] = [
    { value: true, viewValue: 'Activo' },
    { value: false, viewValue: 'Inactivo' },
  ];
  selected = new FormControl('', [Validators.required]);

  watchlist: Watchlist = {
    id: -1,
    athlete_name: '',
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
  watchlists_saved: Watchlist[] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator | any;
  obs: Observable<any> | undefined;
  dataSource: MatTableDataSource<Watchlist> =
    new MatTableDataSource<Watchlist>();

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private dialog: MatDialog,
    private AuthService: AuthService,
    private adminService: WatchlistsAdminServiceService
  ) {}

  selectState(value: boolean, viewValue: string): void {
    this.state.value = value;
    this.state.viewValue = viewValue;
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.watchlists_saved = [];
    this.adminService.getWatchlists().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let watchlist_aux = {
            id: data[i].id,
            athlete_name: data[i].athlete,
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
            watchlist_aux.enabled == this.state.value ||
            this.selected.value == ''
          ) {
            this.watchlists_saved.push(watchlist_aux);
          }
        }
        this.changeDetectorRef.detectChanges();
        this.dataSource = new MatTableDataSource<Watchlist>(
          this.watchlists_saved
        );
        this.dataSource.paginator = this.paginator;
        this.obs = this.dataSource.connect();
        if (this.watchlists_saved.length == 0) {
          let txt = '';
          if (this.state.value) {
            txt = 'activos';
          } else {
            txt = 'inactivos';
          }
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay elementos en la lista de seguimiento ' + txt + '.',
            },
          });
          this.AuthService.getPath();
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'No hay elementos en las listas de seguimiento.',
            },
          });
          this.AuthService.getPath();
        } else {
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Error al cargar los elementos en las listas de seguimiento.',
            },
          });
        }
      }
    );
  }

  changeStateWatchlist(watchlist: Watchlist): void {
    this.adminService.changeStateWatchlist(watchlist).subscribe((response) => {
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

  deleteWatchlist(watchlist: Watchlist): void {
    this.adminService.deleteWatchlist(watchlist).subscribe(
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
