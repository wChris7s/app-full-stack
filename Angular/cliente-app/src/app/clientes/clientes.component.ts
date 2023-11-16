import {Component, OnInit} from '@angular/core';
import {Cliente} from "./cliente";
import {ClienteService} from "./cliente.service";
import Swal from "sweetalert2";
import {tap} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {ModalService} from "./detalle/modal.service";

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[];
  paginador: any[];
  clienteSeleccionado: Cliente;

  constructor (
    private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute,
    private modalService: ModalService,
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
    this.modalService.notificarUpload.subscribe(cliente => {
      this.clientes = this.clientes.map(clienteOriginal => {
        if (cliente.id == clienteOriginal.id) {
          clienteOriginal.foto = cliente.foto;
        }
        return clienteOriginal;
      })
    })
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

  abrirModal(cliente: Cliente) {
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  }
}
