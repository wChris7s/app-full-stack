import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HeaderComponent} from "./header/header.component";
import {FooterComponent} from "./footer/footer.component";
import {DirectivaComponent} from './directiva/directiva.component';
import {ClientesComponent} from './clientes/clientes.component';
import {ClienteService} from "./clientes/cliente.service";
import {LogService} from "./logs/log.service";
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormComponent} from './clientes/form.component';
import {FormsModule} from "@angular/forms";
import {LogComponent} from "./logs/log.component";

const routes: Routes = [
  {path: "", redirectTo: "/clientes", pathMatch: "full"},
  {path: "directivas", component: DirectivaComponent},
  {path: "clientes", component: ClientesComponent},
  {path: "clientes/logs", component: LogComponent},
  {path: "clientes/form", component: FormComponent},
  {path: "clientes/form/:id", component: FormComponent}
]

/**
 * path: Contiene una URL y se mapea con un component.
 * component: componente que será mapeado.
 */

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    DirectivaComponent,
    ClientesComponent,
    LogComponent,
    FormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [ClienteService, LogService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
