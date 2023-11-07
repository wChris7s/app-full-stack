import {Component, OnInit} from "@angular/core";
import {Log} from "./log";
import {LogService} from "./log.service";

@Component({
  selector: 'log-clientes',
  templateUrl: './log.component.html',
})
export class LogComponent implements OnInit {
  logs: Log[];

  constructor(private logService: LogService) {  // Inyección de la dependencia.
  }

  ngOnInit(): void {
    this.logService.getLogs().subscribe(
      (logs) => this.logs = logs
    )
  }

  delete(log: Log): void {
    this.logService.delete(log.id).subscribe(
      reponse => {
        this.logs = this.logs.filter(logActual => logActual != log)
      }
    )
  }
}
