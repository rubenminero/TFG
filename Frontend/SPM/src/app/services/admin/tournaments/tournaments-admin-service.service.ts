import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tournament } from 'src/app/interfaces/tournament/tournament';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EnvService } from 'src/app/services/env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class TournamentsAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getTournaments(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/tournaments',
      httpOptions
    );
  }

  changeStateTournament(tournament: Tournament): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/tournaments/' + tournament.id,
      tournament,
      httpOptions
    );
  }

  deleteTournament(tournament: Tournament): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/tournaments/' + tournament.id,
      httpOptions
    );
  }
}
