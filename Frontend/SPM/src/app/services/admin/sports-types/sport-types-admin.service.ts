import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EnvService } from 'src/app/services/env/env-service.service';
import { AuthService } from '../../auth/auth-service.service';
import { RegisterSportType } from 'src/app/interfaces/sport.type/RegisterSportType';
import { SportType } from 'src/app/interfaces/sport.type/SportType';

@Injectable({
  providedIn: 'root',
})
export class SportsTypesAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getAllSportsTypes(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/sports_types',
      httpOptions
    );
  }

  createSportType(sportType: RegisterSportType): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.post(
      this.envService.getApiUrl() + '/api/sports_types',
      sportType,
      httpOptions
    );
  }

  changeStateSportType(sportType: SportType): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/sports_types/' + sportType.id,
      sportType,
      httpOptions
    );
  }

  deleteSportType(sportType: SportType): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/sports_types/' + sportType.id,
      httpOptions
    );
  }
}
