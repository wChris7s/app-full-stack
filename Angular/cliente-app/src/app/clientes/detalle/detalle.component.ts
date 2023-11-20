import {Component, Input, OnInit} from '@angular/core';
import {Cliente} from "../cliente";
import {ClienteService} from "../cliente.service";
import {ActivatedRoute} from "@angular/router";
import Swal from "sweetalert2";
import {HttpEventType} from "@angular/common/http";
import {ModalService} from "./modal.service";
import {AuthService} from "../../usuarios/auth.service";
import {FacturaService} from "../../facturas/services/factura.service";
import {Factura} from "../../facturas/models/factura";

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {
  @Input() cliente: Cliente;
  titulo: String = "Detalle del clientes";
  fotoSeleccionada: File;
  progreso: number = 0;

  constructor(
    private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute,
    public modalService: ModalService,
    public authService: AuthService,
    public facturaService: FacturaService,
  ) {
  }; // Inyección de dependencias.

  ngOnInit() {}

  seleccionarFoto(event) {
    this.fotoSeleccionada = event.target.files[0];
    this.progreso = 0;
    console.log(this.fotoSeleccionada);
    if (this.fotoSeleccionada.type.indexOf('image') < 0){
      Swal.fire("Error seleccionar imagén.", "El archivo debe ser del tipo imagen.", "error");
      this.fotoSeleccionada = null;
    }
  }

  subirFoto() {
    if (!this.fotoSeleccionada) {
      Swal.fire("Error Upload", "Debe seleccionar una foto", "error");
    } else {
      this.clienteService.subirFoto(this.fotoSeleccionada, this.cliente.id).subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progreso = Math.round((event.loaded/event.total) * 100);
          }
          else if (event.type === HttpEventType.Response) {
            let response: any = event.body;
            this.cliente = response.cliente as Cliente;
            this.modalService.notificarUpload.emit(this.cliente);
            Swal.fire("La foto se ha subido completamente!", response.mensaje , "success");
          }
        }
      );
    }
  }

  cerrarModal () {
    this.modalService.cerrarModal();
    this.fotoSeleccionada = null;
    this.progreso = 0;
  }

  delete(factura: Factura): void {
    Swal.fire({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar la factura \"${factura.descripcion}?\"`,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar.',
      cancelButtonText: 'No, cancelar.',
      buttonsStyling: false,
      reverseButtons: true,
      icon: "warning",
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger',
      }
    }).then((result) => {
      if (result.value) {

        this.facturaService.delete(factura.id).subscribe(
         () => {
           this.cliente.facturas = this.cliente.facturas.filter(f => f !== factura)
           Swal.fire(
            'Factura Eliminada!',
            `Factura ${factura.descripcion} eliminada con éxito.`,
            'success'
           )
         }
        )

      }
    });
  }
}
