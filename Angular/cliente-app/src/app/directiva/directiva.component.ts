import {Component} from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html',
})
export class DirectivaComponent {
  listaCurso: string[] = [
    "Java",
    "TypeScript",
    "C++",
    "C#",
    "Python",
  ]
  public habilitar: boolean = true;


  sethabilitar(): void {
    this.habilitar = (this.habilitar == true) ? false : true
  }
}
