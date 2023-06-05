import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Events } from 'src/app/interfaces/event/event';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EnvService } from 'src/app/services/env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class EventsAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getEvents(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/events',
      httpOptions
    );
  }

  changeStateEvent(event: Events): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/events/' + event.id,
      event,
      httpOptions
    );
  }

  deleteEvent(event: Events): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/events/' + event.id,
      httpOptions
    );
  }
}
