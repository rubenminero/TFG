import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent {
  loading: Boolean = false;
  loginError: String = '';

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.router.navigate(['/home']);
    }
  }

  loginUser(value: any): void {
    let { username, password } = value;

    this.loading = true;
    console.log('logeando');
    this.authService.login(username, password).subscribe(
      (response) => {
        if (response.access_token && response.refresh_token) {
          sessionStorage.setItem('access_token', response.access_token);
          sessionStorage.setItem('refresh_token', response.refresh_token);
          let path = '';
          if (this.authService.getRole() === 'athlete') {
            path = '/home-athlete';
          } else if (this.authService.getRole() === 'organizer') {
            path = '/home-organizer';
          } else if (this.authService.getRole() === 'admin') {
            path = '/home-admin';
          } else {
            path = '/role-error';
          }
          this.router.navigate([path]);
        }
      },
      (error) => {
        if (error.status !== 200)
          this.loginError = 'Las credenciales no son validas';
        this.loading = false;
      },
      () => {
        this.loginError = '';
        this.loading = false;
      }
    );
  }
}
