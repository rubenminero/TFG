import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EnvService } from '../env/env-service.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  httpOptions = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.getAccessToken(),
    },
  };
  constructor(private http: HttpClient, private envService: EnvService) {}

  login(username: string, password: string): Observable<any> {
    let body = {
      username: username,
      password: password,
    };
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/authenticate',
      body
    );
  }
  logout(): Observable<any> {
    let access_token = sessionStorage.getItem('access_token');
    let refresh_token = sessionStorage.getItem('refresh_token');
    sessionStorage.removeItem('access_token');
    sessionStorage.removeItem('refresh_token');
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/logout',
      access_token
    );
  }

  getHeaders(): any {
    return this.httpOptions;
  }

  getAccessToken(): String {
    return sessionStorage.getItem('access_token') || '';
  }

  getRefreshToken(): String {
    return sessionStorage.getItem('refresh_token') || '';
  }

  getId(): number {
    const helper = new JwtHelperService();
    let token = sessionStorage.getItem('access_token');
    let id = -1;
    if (token) {
      let decodedToken = helper.decodeToken(token);
      id = decodedToken['id'];
    }
    return id;
  }

  getRole(): String {
    const helper = new JwtHelperService();
    let token = sessionStorage.getItem('access_token');
    let role = '';
    if (token) {
      let decodedToken = helper.decodeToken(token);
      role = decodedToken['role'];
    }
    return role;
  }

  getSubject() {
    const helper = new JwtHelperService();
    let token = sessionStorage.getItem('access_token');
    let subject = '';
    if (token) {
      let decodedToken = helper.decodeToken(token);
      subject = decodedToken.sub;
    }
    return subject;
  }
}
