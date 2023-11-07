import {Injectable} from '@angular/core';
import {CLIENTES} from "./clientes.json";
import {Cliente} from "./cliente";
import {of, Observable, map} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class ClienteService {
    private urlEndPoint: string = "http://localhost:8081/api/clientes"
    private httpHeaders = new HttpHeaders({
        "Content-Type": "application/json"
    })
    constructor(private http: HttpClient) {
    }

    getClientes(): Observable<Cliente[]> {
        // return of(CLIENTES);    // Convertimos/Creamos el flujo Observable a partir de los objetos Clientes.
        return this.http.get(this.urlEndPoint).pipe(
            map(response => response as Cliente[])
        );
    }

    create(cliente: Cliente) : Observable<Cliente> {
        return this.http.post<Cliente>(this.urlEndPoint, cliente, {headers: this.httpHeaders});
    }

    getCliente(id): Observable<Cliente> {
        return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`);
    }

    update(cliente: Cliente): Observable<Cliente> {
        return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeaders});
    }

    delete(id: number): Observable<Cliente> {
        return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders})
    }
    /**
     * Método síncrono, no podría funcionar en un contexto real con una API-REST
     * Ya que se require manejar peticiones asíncronas que no bloquee la aplicación.
     * getClientes(): Cliente[] {
     *      return CLIENTES;
     * }
     */
}
