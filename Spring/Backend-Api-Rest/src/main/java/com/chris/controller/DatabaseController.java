package com.chris.controller;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;
import com.chris.service.ClienteService;
import com.chris.service.util.ConvertCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class DatabaseController {
   @Autowired
   private ClienteService clienteService;

   @GetMapping("/clientes")
   public List<Cliente> getAll() {
      return clienteService.getAll();
   }

   @GetMapping("/clientes/page/{page}")
   public Page<Cliente> getAll(@PathVariable Integer page) {
      Pageable pageable = PageRequest.of(page, 5);
      return clienteService.getAll(pageable);
   }

   @GetMapping("/clientes/{id}")
   public ResponseEntity<?> getById(@PathVariable Integer id) {
      Cliente cliente;
      Map<String, Object> response = new HashMap<>();

      try {
         cliente = clienteService.findById(id);
      } catch (DataAccessException e) {
         response.put("mensaje", "Error al realizar la consulta en la base de datos.");
         response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      if (cliente == null) {
         response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(cliente, HttpStatus.OK);
   }

   @PostMapping("/clientes")
   public ResponseEntity<?> insert(@Valid @RequestBody ClienteDao clienteDao, BindingResult result) {
      Cliente cliente;
      Map<String, Object> response = new HashMap<>();

      if (result.hasErrors()) {
         List<String> errors = result.getFieldErrors()
          .stream()
          .map(fieldError -> "El campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
          .collect(Collectors.toList());
         response.put("errors", errors);
         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }

      try {
         cliente = clienteService.insert(clienteDao);
      } catch (DataAccessException e) {
         response.put("mensaje", "Error al realizar el insertado en la base de datos.");
         response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("mensaje", "El cliente ha sido creado con éxito!");
      response.put("cliente", cliente);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping("/clientes/{id}")
   public ResponseEntity<?> update(@Valid @RequestBody ClienteDao clienteDao, BindingResult result, @PathVariable Integer id) {
      Cliente cliente = clienteService.findById(id);
      Cliente clienteUpdated;

      Map<String, Object> response = new HashMap<>();

      if (result.hasErrors()) {
         List<String> errors = result.getFieldErrors()
          .stream()
          .map(fieldError -> "El campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
          .collect(Collectors.toList());
         response.put("errors", errors);
         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }

      if (cliente == null) {
         response.put("mensaje", "No se puedo editar, el cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      try {
         cliente.setNombre(clienteDao.getNombre());
         cliente.setApellido(clienteDao.getApellido());
         cliente.setEmail(clienteDao.getEmail());
         clienteUpdated = clienteService.insert(ConvertCliente.convertDtoToDao(cliente));
      } catch (DataAccessException e) {
         response.put("mensaje", "Error al actualizar el cliente en la base de datos.");
         response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("mensaje", "El cliente ha sido actualizado con éxito!.");
      response.put("cliente", clienteUpdated);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @DeleteMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public ResponseEntity<?> delete(@PathVariable Integer id) {
      Map<String, Object> response = new HashMap<>();
      try {
         clienteService.delete(id);
      } catch (DataAccessException e) {
         response.put("mensaje", "Error al eliminar el cliente en la base de datos.");
         response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      response.put("mensaje", "El cliente ha sido eliminado con éxito!");
      return new ResponseEntity<>(response, HttpStatus.OK);
   }
}
