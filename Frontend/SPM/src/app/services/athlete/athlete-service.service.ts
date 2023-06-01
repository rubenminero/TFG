import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { RegisterAthlete } from '../../interfaces/athlete/RegisterAthlete';
import { Athlete } from '../../interfaces/athlete/Athlete';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class AthleteServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getAthletePath(): String {
    return this.authService.getPathHome();
  }

  registerAthlete(athlete: RegisterAthlete): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
      },
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/register-athlete',
      athlete,
      httpOptions
    );
  }

  getAthlete(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    console.log(httpOptions);
    return this.http.get(
      this.envService.getApiUrl() + '/api/athletes/' + id,
      httpOptions
    );
  }

  updateAthlete(athlete: Athlete): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/athletes/' + this.authService.getId(),
      athlete,
      httpOptions
    );
  }
}
