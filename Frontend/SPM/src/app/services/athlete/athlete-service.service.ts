import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';
import { Athlete } from 'src/app/interfaces/athlete/Athlete';
import { RegisterAthlete } from 'src/app/interfaces/athlete/RegisterAthlete';

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

  getAthlete(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/athletes/' + id,
      httpOptions
    );
  }

  registerAthlete(athlete: RegisterAthlete): Observable<any> {
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/register-athlete',
      athlete
    );
  }

  deleteAthlete(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.delete(
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
