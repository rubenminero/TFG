import { Tournament } from './../../interfaces/tournament/tournament';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth-service.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EnvService } from '../env/env-service.service';
import { RegisterInscription } from 'src/app/interfaces/inscriptions/register-inscription';
import { Inscriptions } from 'src/app/interfaces/inscriptions/inscriptions';

@Injectable({
  providedIn: 'root',
})
export class InscriptionServiceService {
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
      this.envService.getApiUrl() +
        '/api/athletes/inscriptions/' +
        this.authService.getId(),
      httpOptions
    );
  }

  addTournamentToInscription(
    inscription: RegisterInscription
  ): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/inscriptions',
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
      this.envService.getApiUrl() + '/api/inscriptions/' + inscription.id,
      httpOptions
    );
  }

  getInscriptionsFromTournament(id: number): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.get(
      this.envService.getApiUrl() +
        '/api/tournaments/inscriptions/' +
        this.authService.getId(),
      httpOptions
    );
  }
}
