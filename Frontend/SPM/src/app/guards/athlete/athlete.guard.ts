import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth/auth-service.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AthleteGuard {
  constructor(private router: Router, private authService: AuthService) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    let role = this.authService.getRole();
    if (role == 'ATHLETE') {
      return true;
    } else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
