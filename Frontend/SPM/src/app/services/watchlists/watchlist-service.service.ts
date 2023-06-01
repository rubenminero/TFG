import { Watchlist } from './../../interfaces/watchlists/watchlist';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { Observable } from 'rxjs';
import { AuthService } from './../auth/auth-service.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';
import { RegisterWatchlist } from 'src/app/interfaces/watchlists/register-watchlist';
import { RegisterInscription } from 'src/app/interfaces/inscriptions/register-inscription';

@Injectable({
  providedIn: 'root',
})
export class WatchlistService {
  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private envService: EnvService
  ) {}

  addToWatchlist(watchlist: RegisterWatchlist): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/watchlists',
      watchlist,
      httpOptions
    );
  }
  getWatchlists(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() +
        '/api/athletes/watchlists/' +
        this.authService.getId(),
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
      this.envService.getApiUrl() + '/api/watchlists/' + watchlist.id,
      httpOptions
    );
  }
}
