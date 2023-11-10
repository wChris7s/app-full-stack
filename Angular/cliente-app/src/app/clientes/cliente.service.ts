import {Injectable} from '@angular/core';
import {Cliente} from "./cliente";
import {catchError, map, Observable, throwError} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import Swal from "sweetalert2";
import {Router} from "@angular/router";

@Injectable()
export class ClienteService {
  private urlEndPoint: string = "http://localhost:8081/api/clientes"
  private httpHeaders = new HttpHeaders({
    "Content-Type": "application/json"
  })

  constructor(private http: HttpClient, private router: Router) {
  }

  getClientes(): Observable<Cliente[]> {
    // return of(CLIENTES);    // Convertimos/Creamos el flujo Observable a partir de los objetos Clientes.
    return this.http.get(this.urlEndPoint).pipe(
      map(response => response as Cliente[])
    );
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.urlEndPoint, cliente, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.cliente as Cliente),
      catchError(err => {
        Swal.fire("Error al crear al cliente", err.error.mensaje, "error");
        return throwError(err);
      })
    );;
  }

  getCliente(id): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(err => {
        this.router.navigate(['/clientes']);
        Swal.fire("Error al editar", err.error.mensaje, "error");
        return throwError(err);
      })
    )
  }

  update(cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.cliente as Cliente),  // Toma la key "cliente" para que al momento de tratar el json no exista interferencia con "mensaje".
      catchError(err => {
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

  /**
   * Método síncrono, no podría funcionar en un contexto real con una API-REST
   * Ya que se require manejar peticiones asíncronas que no bloquee la aplicación.
   * getClientes(): Cliente[] {
   *      return CLIENTES;
   * }
   */
}
