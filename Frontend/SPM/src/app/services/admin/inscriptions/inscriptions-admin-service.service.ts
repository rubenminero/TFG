import { Injectable } from '@angular/core';
import { Inscriptions } from 'src/app/interfaces/inscriptions/inscriptions';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EnvService } from 'src/app/services/env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class InscriptionsAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getInscriptions(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/inscriptions',
      httpOptions
    );
  }

  changeStateInscription(inscription: Inscriptions): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() +
        '/api/admins/inscriptions/' +
        inscription.id,
      inscription,
      httpOptions
    );
  }

  deleteInscription(inscription: Inscriptions): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() +
        '/api/admins/inscriptions/' +
        inscription.id,
      httpOptions
    );
  }
}
