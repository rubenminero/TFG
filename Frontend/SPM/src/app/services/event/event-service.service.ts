import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';
import { Events } from 'src/app/interfaces/event/event';
import { RegisterEvent } from 'src/app/interfaces/event/register-event';

@Injectable({
  providedIn: 'root',
})
export class EventServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  createEvent(event: RegisterEvent): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/events',
      event,
      httpOptions
    );
  }

  getEvents(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/events',
      httpOptions
    );
  }
  getEvent(id: Number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() + '/api/events/' + id,
      httpOptions
    );
  }

  updateEvent(event: Events): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/events/' + event.id,
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
      this.envService.getApiUrl() + '/api/tournaments/' + event.id,
      httpOptions
    );
  }
}
