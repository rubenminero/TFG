import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from 'src/app/components/shared/pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent {
  loading: Boolean = false;
  loginError: String = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    let token = sessionStorage.getItem('token');
    if (token) {
      this.authService.getPath();
    }
  }

  loginUser(value: any): void {
    let { username, password } = value;

    this.loading = true;
    this.authService.login(username, password).subscribe(
      (response) => {
        if (response.access_token && response.refresh_token) {
          sessionStorage.setItem('access_token', response.access_token);
          sessionStorage.setItem('refresh_token', response.refresh_token);
          if (!this.authService.getEnabled()) {
            let mssg = '';
            if (this.authService.getRole() == 'ORGANIZER') {
              mssg = 'Su cuenta de organizador no ha sido activada.';
            } else if (this.authService.getRole() == 'ATHLETE') {
              mssg = 'Su cuenta de atleta ha sido desactivada.';
            }
            this.dialog
              .open(PopUpMsgComponent, {
                data: {
                  msg: mssg,
                },
              })
              .afterClosed()
              .subscribe(() => {
                this.authService.logout();
              });
          } else {
            this.authService.getPath();
          }
        }
      },
      (error) => {
        if (error.status == 403) {
          this.loginError = 'Su cuenta no ha sido activada.';
        } else {
          this.loginError = 'Las credenciales no son validas';
        }
        this.loading = false;
      },
      () => {
        this.loginError = '';
        this.loading = false;
      }
    );
  }
}
