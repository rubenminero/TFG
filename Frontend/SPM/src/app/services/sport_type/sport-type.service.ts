import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';
import { AuthService } from '../auth/auth-service.service';
import { RegisterSportType } from 'src/app/interfaces/sport.type/RegisterSportType';
import { SportType } from 'src/app/interfaces/sport.type/SportType';

@Injectable({
  providedIn: 'root',
})
export class SportTypeService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getSportTypes(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/sports_types',
      httpOptions
    );
  }
}
