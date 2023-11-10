import {Component, OnInit} from '@angular/core';
import {Cliente} from "./cliente";
import {ClienteService} from "./cliente.service";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from 'sweetalert2'

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
})
export class FormComponent implements OnInit {
  cliente: Cliente = new Cliente();
  titulo: string = "Crear Cliente";
  errores: String[];

  constructor(private clienteService: ClienteService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.cargarCliente();
  }

  cargarCliente(): void {
    this.activatedRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => {
          this.cliente = cliente;
        })
      }
    })
  }

  public create(): void {
    this.clienteService.create(this.cliente)  // Retorna una lista de Observables.
      .subscribe({
        next: (cliente) => {
          this.router.navigate(['/clientes']);
          Swal.fire('Nuevo Cliente', `Cliente ${cliente.nombre} creado con éxito!`, 'success');
        },
        error: (e) => {
          this.errores = e.error.errors as String[];
        }
      });
  }

/*  public create(): void {
    // Una vez se inserta el dato. se redirige a la ruta /clientes.
    this.clienteService.create(this.cliente)
      .subscribe(
        (cliente) => {  // En este caso ya tiene el Json transformado, por lo que no existe interferencia.
          this.router.navigate(['/clientes']);
          Swal.fire('Nuevo Cliente', `Cliente ${cliente.nombre} creado con éxito!`, 'success')

          // (json) => {
          //  this.router.navigate(['/clientes']);
          //  Swal.fire('Nuevo Cliente', `Cliente ${json.cliente.nombre} creado con éxito!`, 'success')
          // }
        },
        err => {
          this.errores = err.error.errors as String[];
        }
      );
  }*/

  update(): void {
    this.clienteService.update(this.cliente)
      .subscribe((cliente) => {
        this.router.navigate(['/clientes']);
        Swal.fire("Cliente Actualizado", `Cliente ${cliente.nombre} actualizado con éxito!`, 'success')
      })
  }
}
