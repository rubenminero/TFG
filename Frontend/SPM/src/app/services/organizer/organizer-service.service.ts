import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterOrganizer } from 'src/app/interfaces/organizer/register-organizer';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth-service.service';
import { EnvService } from '../env/env-service.service';
import { Organizer } from 'src/app/interfaces/organizer/organizer';

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

  getOrganizer(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/organizers/' + id,
      httpOptions
    );
  }

  getTournamentsOrganizer(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() +
        '/api/organizers/tournaments/' +
        this.authService.getId(),
      httpOptions
    );
  }

  getEventsOrganizer(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() +
        '/api/organizers/events/' +
        this.authService.getId(),
      httpOptions
    );
  }

  updateOrganizer(organizer: Organizer): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() +
        '/api/organizers/' +
        this.authService.getId(),
      organizer,
      httpOptions
    );
  }

  deleteOrganizer(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.delete(
      this.envService.getApiUrl() + '/api/organizers/' + id,
      httpOptions
    );
  }
}
