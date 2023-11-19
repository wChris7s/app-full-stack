import { Injectable } from '@angular/core';
import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import {catchError, Observable, throwError} from 'rxjs';
import {AuthService} from "../auth.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService, private router: Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler):
     Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
         catchError (err => {
             // 401 No Autorizado => Carece de credenciales válidas de autenticación.
             // 403 Acceso denegado => El servidor se niega a permitir la acción solicitada.
             if (err.status == 401) {
                 if (this.authService.isAuthenticated()) {
                     this.authService.logout();
                 }
                 this.router.navigate(['/login']);
             }
             if (err.status == 403) {
                 Swal.fire(
                  "Acceso denegado",
                  `Hola ${this.authService.usuario.username}, no tienes acceso a este recurso`,
                  "warning"
                 );
                 this.router.navigate(['/clientes']);
             }
             return throwError(() => err);
         })
        );
    }
}