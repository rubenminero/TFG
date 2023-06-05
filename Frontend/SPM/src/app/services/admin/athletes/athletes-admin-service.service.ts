import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../../env/env-service.service';
import { Athlete } from 'src/app/interfaces/athlete/Athlete';

@Injectable({
  providedIn: 'root',
})
export class AthletesAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getAthletes(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/athletes',
      httpOptions
    );
  }

  changeStateAthlete(athlete: Athlete): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/athletes/' + athlete.id,
      athlete,
      httpOptions
    );
  }

  deleteAthlete(athlete: Athlete): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/athletes/' + athlete.id,
      httpOptions
    );
  }
}
