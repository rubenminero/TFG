import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-navbar-athlete',
  templateUrl: './navbar-athlete.component.html',
  styleUrls: ['./navbar-athlete.component.scss'],
})
export class NavbarAthleteComponent {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private AuthService: AuthService
  ) {}

  ngOnInit(): void {
    this.router.navigate(['/athletes-menu/welcome']);
  }

  logout(): void {
    this.AuthService.logout();
    this.router.navigate(['login']);
  }
}
