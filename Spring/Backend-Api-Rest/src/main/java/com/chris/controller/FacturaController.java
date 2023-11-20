package com.chris.controller;

import com.chris.entity.Factura;
import com.chris.entity.Producto;
import com.chris.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api")
public class FacturaController {
   @Autowired
   private ClienteService clienteService;

   @Secured({"ROLE_ADMIN", "ROLE_USER"})
   @GetMapping("/facturas/{id}")
   @ResponseStatus(HttpStatus.OK)
   public Factura getFacturaById(@PathVariable Integer id) {
      return clienteService.findFacturaById(id);
   }

   @Secured("ROLE_ADMIN")
   @DeleteMapping("/facturas/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteFacturaById(@PathVariable Integer id) {
      clienteService.deleteFacturaById(id);
   }

   @Secured("ROLE_ADMIN")
   @GetMapping("/facturas/filtrar-productos/{term}")
   @ResponseStatus(HttpStatus.OK)
   public List<Producto> filtrarProductos(@PathVariable String term) {
      return clienteService.findProductoByNombre(term);
   }

   @Secured("ROLE_ADMIN")
   @PostMapping("/facturas")
   @ResponseStatus(HttpStatus.CREATED)
   public Factura crearFactura(@RequestBody Factura factura) {
      return clienteService.insertFactura(factura);
   }
}
