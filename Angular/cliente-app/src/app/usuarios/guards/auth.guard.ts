import { CanActivateFn, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (
 route: ActivatedRouteSnapshot,
 state: RouterStateSnapshot
): Observable<boolean> | Promise<boolean> | boolean => {
  let authService = inject(AuthService); // Inyecta AuthService
  let router = inject(Router); // Inyecta Router

  if (authService.isAuthenticated()) {
    if (isTokenExpirado(authService)) {
      authService.logout();
      router.navigate(['/login']);
      return false;
    }
    return true;
  }
  else {
    router.navigate(['/login']);
    return false;
  }
};

function isTokenExpirado(authService: AuthService): boolean {
  let token = authService.token;
  let payload = authService.getTokenData(token);
  let now = new Date().getTime() / 1000;
  if (payload.exp < now) {
    return true;
  } else {
    return false;
  }
}