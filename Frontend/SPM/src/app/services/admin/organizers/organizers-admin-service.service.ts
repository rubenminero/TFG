import { Organizer } from 'src/app/interfaces/organizer/organizer';
import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../../env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class OrganizersAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getOrganizers(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/organizers',
      httpOptions
    );
  }

  changeStateOrganizer(organizer: Organizer): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/organizers/' + organizer.id,
      organizer,
      httpOptions
    );
  }

  deleteOrganizer(organizer: Organizer): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/organizers/' + organizer.id,
      httpOptions
    );
  }
}
