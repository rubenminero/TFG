import { Injectable } from '@angular/core';
import { Watchlist } from 'src/app/interfaces/watchlists/watchlist';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EnvService } from 'src/app/services/env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class WatchlistsAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getWatchlists(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/watchlists',
      httpOptions
    );
  }

  changeStateWatchlist(watchlist: Watchlist): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/watchlists/' + watchlist.id,
      watchlist,
      httpOptions
    );
  }

  deleteWatchlist(watchlist: Watchlist): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/watchlists/' + watchlist.id,
      httpOptions
    );
  }
}
