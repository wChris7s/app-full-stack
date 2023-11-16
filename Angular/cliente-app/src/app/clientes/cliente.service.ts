import {Injectable} from '@angular/core';
import {DatePipe} from "@angular/common";
import {Cliente} from "./cliente";
import {catchError, map, Observable, tap, throwError} from "rxjs";
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Region} from "./region";


@Injectable()
export class ClienteService {
  private urlEndPoint: string = "http://localhost:8081/api/clientes"
  private httpHeaders = new HttpHeaders({
    "Content-Type": "application/json"
  })

  constructor(private http: HttpClient, private router: Router) {
  }

  getRegiones(): Observable<Region[]> {
    return this.http.get<Region[]>(this.urlEndPoint + "/regiones");
  }

  getClientes(page: number): Observable<any> {
    return this.http.get(this.urlEndPoint + '/page/' + page)
      .pipe(
        map((response: any) => {
          (response.content as Cliente[]).map(
            cliente => {
              cliente.nombre = cliente.nombre.toUpperCase();
              return cliente;
            })
          return response;
        })
      );
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post(this.urlEndPoint, cliente, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.cliente as Cliente),
      catchError(err => {

        if (err.status == 400) {  // Código de error 400.
          return throwError(err);
        }

        Swal.fire("Error al crear al cliente", err.error.mensaje, "error");
        return throwError(err);
      })
    );
  }

  getCliente(id): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(err => {
        this.router.navigate(['/clientes']);
        Swal.fire("Error al editar", err.error.mensaje, "error");
        return throwError(err);
      })
    );
  }

  update(cliente: Cliente): Observable<Cliente> {
    return this.http.put(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.cliente as Cliente),  // Toma la key "cliente" para que al momento de tratar el json no exista interferencia con "mensaje".
      catchError(err => {

        if (err.status == 400) {  // Código de error 400.
          return throwError(err);
        }

        Swal.fire("Error al editar al cliente", err.error.mensaje, "error");
        return throwError(err);
      })
    );
  }

  delete(id: number): Observable<Cliente> {
    return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(err => {
        Swal.fire("Error al eliminar al cliente", err.error.mensaje, "error");
        return throwError(err);
      })
    );
  }

  subirFoto(archivo: File, id: number) {
    let formData = new FormData();  // Soporte multipart-form-data
    formData.append("archivo", archivo);
    formData.append("id", id.toString());

    const req = new HttpRequest("POST", `${this.urlEndPoint}/upload`, formData, {
      reportProgress: true
    });

    return this.http.request(req);
  }
}
