import {Component, OnInit} from '@angular/core';
import {Cliente} from "./cliente";
import {ClienteService} from "./cliente.service";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from 'sweetalert2'
import {Region} from "./region";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
})
export class FormComponent implements OnInit {
  cliente: Cliente = new Cliente();
  regiones: Region[]
  titulo: string = "Crear Cliente";
  errores: String[];

  constructor(private clienteService: ClienteService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      let id = +params['id'];
      if (id) {
        this.clienteService.getCliente(id).subscribe((cliente) => {
          this.cliente = cliente;
        })
      }
    });
    this.clienteService.getRegiones().subscribe(regiones => this.regiones = regiones);
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

  update(): void {
    this.clienteService.update(this.cliente)
      .subscribe((cliente) => {
        this.router.navigate(['/clientes']);
        Swal.fire("Cliente Actualizado", `Cliente ${cliente.nombre} actualizado con éxito!`, 'success')
      });
  }

  compararRegion(o1: Region, o2: Region) {
    // return (o1 == null || o2 == null) ? false : (o1.id) === (o2.id);
    if (o1 === undefined && o2 === undefined) {
      return true;
    }
    return (o1 === null || o2 === null || o1 === undefined || o2 === undefined) ? false : (o1.id) === (o2.id);  // Más estricto.
  }
}
