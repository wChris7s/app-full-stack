import {Injectable} from '@angular/core';
import {Cliente} from "./cliente";
import {catchError, map, Observable, throwError} from "rxjs";
import {HttpClient, HttpRequest} from "@angular/common/http";
import {Router} from "@angular/router";
import {Region} from "./region";


@Injectable()
export class ClienteService {
    private urlEndPoint: string = "http://localhost:8081/api/clientes"

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
                   // cliente.nombre = cliente.nombre.toUpperCase();
                   return cliente;
               })
              return response;
          })
         );
    }

    create(cliente: Cliente): Observable<Cliente> {
        return this.http.post(this.urlEndPoint, cliente).pipe(
         map((response: any) => response.cliente as Cliente),
         catchError(err => {
             if (err.status == 400) {  // Código de error 400.
                 return throwError(() => err);
             }
             return throwError(() => err);
         })
        );
    }

    getCliente(id): Observable<Cliente> {
        return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
         catchError(err => {
             if (err.status != 401) {
                 this.router.navigate(['/clientes']);
             }
             return throwError(() => err);
         })
        );
    }

    update(cliente: Cliente): Observable<Cliente> {
        return this.http.put(`${this.urlEndPoint}/${cliente.id}`, cliente).pipe(
         map((response: any) => response.cliente as Cliente),  // Toma la key "cliente" para que al momento de tratar el json no exista interferencia con "mensaje".
         catchError(err => {
             if (err.status == 400) {  // Código de error 400.
                 return throwError(() => err);
             }
             return throwError(() => err);
         })
        );
    }

    delete(id: number): Observable<Cliente> {
        return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
         catchError(err => {
             return throwError(() => err);
         })
        );
    }

    subirFoto(archivo: File, id: number) {
        let formData = new FormData();  // Soporte multipart-form-data
        formData.append("archivo", archivo);
        formData.append("id", id.toString());
        const req = new HttpRequest("POST", `${this.urlEndPoint}/upload`, formData, {
            reportProgress: true,
        });
        return this.http.request(req);
    }
}
