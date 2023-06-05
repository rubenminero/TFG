import { Injectable } from '@angular/core';
import { Token } from 'src/app/interfaces/token/token';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { EnvService } from 'src/app/services/env/env-service.service';

@Injectable({
  providedIn: 'root',
})
export class TokensAdminServiceService {
  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private envService: EnvService
  ) {}

  getTokens(): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.get(
      this.envService.getApiUrl() + '/api/admins/tokens',
      httpOptions
    );
  }

  changeStateToken(token: Token): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };
    return this.http.put(
      this.envService.getApiUrl() + '/api/admins/tokens/' + token.id,
      token,
      httpOptions
    );
  }

  deleteToken(token: Token): Observable<any> {
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.authService.getAccessToken(),
      },
    };

    return this.http.delete(
      this.envService.getApiUrl() + '/api/admins/tokens/' + token.id,
      httpOptions
    );
  }
}
