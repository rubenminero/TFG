import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterOrganizer } from 'src/app/interfaces/organizer/register-organizer';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service.service';
import { EnvService } from '../env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class OrganizerServiceService {
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

  getOrganizerPath(): String {
    return this.authService.getPathHome();
  }

  registerOrganizer(organizer: RegisterOrganizer): Observable<any> {
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/register-organizer',
      organizer
    );
  }
}
