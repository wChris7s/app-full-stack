import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from "rxjs";
import {inject} from "@angular/core";
import {AuthService} from "../auth.service";
import Swal from "sweetalert2";

export const roleGuard: CanActivateFn = (
 route: ActivatedRouteSnapshot,
 state: RouterStateSnapshot
): Observable<boolean> | Promise<boolean> | boolean => {
  let authService = inject(AuthService); // Inyecta AuthService
  let router = inject(Router); // Inyecta Route

  if (!authService.isAuthenticated()) {
    router.navigate(['/login']);
    return false;
  }

  let role = route.data['role'] as string;
  console.log(role);
  if (authService.hasRole(role)) {
    return true;
  }
  Swal.fire(
     "Acceso denegado",
     `Hola ${authService.usuario.username}, no tienes acceso a este recurso`,
     "warning"
    );
    router.navigate(['/clientes']);
    return false;
};
