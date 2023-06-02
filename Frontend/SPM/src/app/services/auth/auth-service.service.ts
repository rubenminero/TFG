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
  httpOptionsForRefresh = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + this.getRefreshToken(),
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
<<<<<<< Updated upstream
=======
    const httpOptions = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + access_token,
      },
    };
>>>>>>> Stashed changes
    sessionStorage.removeItem('access_token');
    sessionStorage.removeItem('refresh_token');
    return this.http.post(
      this.envService.getApiUrl() + '/api/auth/logout',
      access_token
    );
  }

<<<<<<< Updated upstream
  getHeaders(): any {
    return this.httpOptions;
=======
  changePassword(
    id_user: number,
    oldpassword: string,
    password: string,
    confirmpassword: string
  ): Observable<any> {
    let body = {
      id_user: id_user,
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
    let path = '';
    const role = this.getRole();

    if (role === 'ADMIN') {
      path = '/admins';
    } else if (role === 'ORGANIZER') {
      path = '/organizers';
    } else {
      path = '/athletes';
    }

    return this.http.put(
      this.envService.getApiUrl() + '/api' + path + '/password',
      body,
      httpOptions
    );
>>>>>>> Stashed changes
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
    if (role === 'athlete') {
      path = '/home-athlete';
    } else if (role === 'organizer') {
      path = '/home-organizer';
    } else if (role === 'admin') {
      path = '/home-admin';
    } else {
      path = '/role-error';
    }
    return path;
  }
}
