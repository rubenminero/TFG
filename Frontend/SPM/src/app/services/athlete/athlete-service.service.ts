import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { RegisterAthlete } from './../../interfaces/athelete/RegisterAthlete';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class AthleteServiceService {
  httpOptions = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.authService.getAccessToken(),
    },
  };
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getAthletePath(): String {
    return this.authService.getPathHome();
  }

  registerAthlete(athlete: RegisterAthlete): Observable<any> {
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/register-athlete',
      athlete
    );
  }
}
