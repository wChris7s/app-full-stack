import {Component, OnInit} from '@angular/core';
import {Cliente} from "./cliente";
import {ClienteService} from "./cliente.service";
import Swal from "sweetalert2";
import {tap} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[];
  paginador: any[];

  constructor (
    private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute
  ) {};

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      let page: number = +params.get("page");
      if (!page) {
        page = 0;
      }
      this.clienteService.getClientes(page).pipe()
        .subscribe((response) => {
          this.clientes = response.content as Cliente[]
          this.paginador = response;
        });
    });
  }

  delete(cliente: Cliente): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: "btn btn-success",
        cancelButton: "btn btn-danger"
      },
      buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
      title: "¿Está seguro?",
      text: `¿Seguro que desea eliminar al cliente ${cliente.nombre}?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Si, eliminar!",
      cancelButtonText: "No, cancelar!",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.clienteService.delete(cliente.id).subscribe(
          (response => {
            this.clientes = this.clientes.filter(clienteActual => clienteActual != cliente)
            swalWithBootstrapButtons.fire({
              title: "Cliente Eliminado!",
              text: `Cliente ${cliente.nombre} eliminado con éxito.`,
              icon: "success"
            });
          })
        )
      }
    });
  }
}
