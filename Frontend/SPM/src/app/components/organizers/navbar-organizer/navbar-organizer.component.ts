import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-navbar-organizer',
  templateUrl: './navbar-organizer.component.html',
  styleUrls: ['./navbar-organizer.component.scss'],
})
export class NavbarOrganizerComponent {
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
    this.router.navigate(['/organizers-menu/welcome']);
  }

  logout(): void {
    this.AuthService.logout().subscribe((data) => {
      console.log(data);
      this.AuthService.revokeTokens();
      this.AuthService.getPath();
    });
  }
}
