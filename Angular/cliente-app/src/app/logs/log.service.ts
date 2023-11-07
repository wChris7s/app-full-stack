import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Log} from "./log"

@Injectable()
export class LogService {
  private urlEndPoint: string = "http://localhost:8081/api/logs"
  private httpHeaders = new HttpHeaders({
    "Content-Type": "application/json"
  })

  constructor(private http: HttpClient) {
  }

  getLogs(): Observable<Log[]> {
    return this.http.get(this.urlEndPoint)
      .pipe(
        map(response => response as Log[])
      )
  }

  delete(id: number): Observable<Log> {
    return this.http.delete(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders})
      .pipe(
        map(response => response as Log)
      )
  }
}
