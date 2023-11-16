import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HeaderComponent} from "./header/header.component";
import {FooterComponent} from "./footer/footer.component";
import {DirectivaComponent} from './directiva/directiva.component';
import {ClientesComponent} from './clientes/clientes.component';
import {ClienteService} from "./clientes/cliente.service";
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormComponent} from './clientes/form.component';
import {FormsModule} from "@angular/forms";
import {registerLocaleData} from "@angular/common";
import localePE from "@angular/common/locales/es-PE";
import {PaginatorComponent} from './paginator/paginator.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import { DetalleComponent } from './clientes/detalle/detalle.component';


registerLocaleData(localePE, 'es');
const routes: Routes = [
  {path: "", redirectTo: "/clientes", pathMatch: "full"},
  {path: "directivas", component: DirectivaComponent},
  {path: "clientes", component: ClientesComponent},
  {path: "clientes/page/:page", component: ClientesComponent},
  {path: "clientes/form", component: FormComponent},
  {path: "clientes/form/:id", component: FormComponent},
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
    FormComponent,
    PaginatorComponent,
    DetalleComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [ClienteService, {provide: LOCALE_ID, useValue: "es-PE"}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
