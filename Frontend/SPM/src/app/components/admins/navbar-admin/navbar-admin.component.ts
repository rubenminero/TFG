import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { Observable } from 'rxjs';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map, shareReplay, tap } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { PopUpCreateSportTypeComponent } from '../../shared/pop-ups/pop-up-create-sport-type/pop-up-create-sport-type.component';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar-admin.component.html',
  styleUrls: ['./navbar-admin.component.scss'],
})
export class NavbarAdminComponent {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private AuthService: AuthService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.router.navigate(['/admins-menu/welcome']);
  }

  createSportType(): void {
    this.dialog.open(PopUpCreateSportTypeComponent, {});
  }

  logout(): void {
    this.AuthService.logout().subscribe((data) => {
      console.log(data);
      this.AuthService.revokeTokens();
      this.AuthService.getPath();
    });
  }
}
