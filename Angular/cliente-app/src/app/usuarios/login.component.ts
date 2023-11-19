import {Component, OnInit} from '@angular/core';
import {Usuario} from "./usuario";
import * as sweetalert2 from "sweetalert2";
import Swal from "sweetalert2";
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
    // Atributos
    titulo: string = "Sign-In"
    usuario: Usuario;

    // Constructor
    constructor(private authService: AuthService, private router: Router) {
        this.usuario = new Usuario();
    }
    ngOnInit(): void {
        if (this.authService.isAuthenticated()) {
            Swal.fire(
             "Login",
             `Hola ${this.authService.usuario.username} ya estás autenticado.`,
             "info"
            )
            this.router.navigate(['/clientes']);
        }
    }


    // Métodos
    login(): void {
        console.log(this.usuario);
        if (this.usuario.username == null || this.usuario.password == null) {
            Swal.fire(
             "Error Login",
             "El usuario o la contraseña no puede ser vacío.",
             "error"
            );
            return;
        }

        this.authService.login(this.usuario).subscribe(response => {
            console.log(response);
            this.authService.guardarUsuario(response.access_token);
            this.authService.guardarToken(response.access_token);
            let usuario = this.authService.usuario; // Getter
            this.router.navigate(['/clientes']);
            Swal.fire(
             "Login",
             `Hola ${usuario.username}, has iniciado sesión con éxito.`,
             'success'
            )
        }, error => {
            if (error.status == 400) {
                Swal.fire(
                 'Login',
                 `Usuario o clave incorrecta.`,
                 'error'
                )
            }
        });
    }
}
