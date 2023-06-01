import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EnvService } from '../env/env-service.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private envService: EnvService,
    private router: Router
  ) {}

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
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.getAccessToken(),
      },
    };
    let access_token = sessionStorage.getItem('access_token');
    let refresh_token = sessionStorage.getItem('refresh_token');
    sessionStorage.removeItem('access_token');
    sessionStorage.removeItem('refresh_token');
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/logout',
      httpOptions
    );
  }

  changePassword(
    oldpassword: string,
    password: string,
    confirmpassword: string
  ): Observable<any> {
    let body = {
      oldpassword: oldpassword,
      password: password,
      confirmpassword: confirmpassword,
    };

    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.getAccessToken(),
      },
    };
    console.log(body);
    return this.http.put(
      this.envService.getApiUrl() + '/api/auth/password/' + this.getId(),
      body,
      httpOptions
    );
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

  getPathHome(): String {
    let path = '';
    let role = this.getRole();

    if (role === 'ATHLETE') {
      path = '/athletes-menu';
    } else if (role === 'ORGANIZER') {
      path = '/organizers-menu';
    } else if (role === 'ROLE_ADMIN') {
      path = '/admins-menu';
    } else {
      path = '';
    }
    console.log(path);
    console.log(role);
    return path;
  }

  getPath(): void {
    let path = '';
    let role = this.getRole();
    console.log(path);
    console.log(role);
    if (role === 'ATHLETE') {
      this.router.navigate(['/athletes-menu']);
    } else if (role === 'ORGANIZER') {
      this.router.navigate(['/organizers-menu']);
    } else if (role === 'ROLE_ADMIN') {
      this.router.navigate(['/admins-menu']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}
