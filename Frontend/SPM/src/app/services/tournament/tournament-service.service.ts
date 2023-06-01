import { Tournament } from './../../interfaces/tournament/tournament';
import { RegisterTournament } from './../../interfaces/tournament/register-tournament';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class TournamentServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  createTournament(tournament: RegisterTournament): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/tournaments',
      tournament,
      httpOptions
    );
  }

  getTournaments(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/tournaments',
      httpOptions
    );
  }
  getTournament(tournament_id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/tournaments/' + tournament_id,
      httpOptions
    );
  }

  updateTournament(tournament: Tournament): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/tournaments/' + tournament.id,
      tournament,
      httpOptions
    );
  }
}
