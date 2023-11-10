package com.chris.controller;

import com.chris.dao.ClienteDao;
import com.chris.dao.LogDao;
import com.chris.model.Cliente;
import com.chris.model.Log;
import com.chris.service.ClienteService;
import com.chris.service.LogService;
import com.chris.service.util.ConvertCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class DatabaseController {
   @Autowired
   private ClienteService clienteService;

   @Autowired
   private LogService logService;

   @GetMapping("/clientes")
   public List<Cliente> getAll() {
      List<Cliente> clientes = clienteService.getAll();
      logService.insertGetAll(clientes);
      return clientes;
   }

   @GetMapping("/clientes/{id}")
   public ResponseEntity<?> getById(@PathVariable Integer id) {
      Cliente cliente;
      Map<String, Object> response = new HashMap<>();

      try {
         cliente = clienteService.findById(id);
      } catch (DataAccessException e) {
         response.put("Mensaje", "Error al realizar la consulta en la base de datos.");
         response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      if (cliente == null) {
         response.put("Mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      logService.insertGetById(cliente);
      return new ResponseEntity<>(cliente, HttpStatus.OK);
   }

   @PostMapping("/clientes")
   public ResponseEntity<?> insert(@RequestBody ClienteDao clienteDao) {
      Cliente cliente;
      Map<String, Object> response = new HashMap<>();
      try {
         cliente = clienteService.insert(clienteDao);
      } catch (DataAccessException e) {
         response.put("Mensaje", "Error al realizar el insertado en la base de datos.");
         response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      logService.insert(cliente);
      response.put("Mensaje", "El cliente ha sido creado con éxito!");
      response.put("Cliente", cliente);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @PutMapping("/clientes/{id}")
   public ResponseEntity<?> update(@RequestBody ClienteDao clienteDao, @PathVariable Integer id) {
      Cliente cliente = clienteService.findById(id);
      Cliente clienteUpdated;

      Map<String, Object> response = new HashMap<>();
      if (cliente == null) {
         response.put("Mensaje", "No se puedo editar, el cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
      }

      try {
         cliente.setNombre(clienteDao.getNombre());
         cliente.setApellido(clienteDao.getApellido());
         cliente.setEmail(clienteDao.getEmail());
         clienteUpdated = clienteService.insert(ConvertCliente.convertDtoToDao(cliente));
      } catch (DataAccessException e) {
         response.put("Mensaje", "Error al actualizar el cliente en la base de datos.");
         response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      logService.insertUpdate(cliente, ConvertCliente.convertDaoToDto(clienteDao));
      response.put("Mensaje", "El cliente ha sido actualizado con éxito!.");
      response.put("Cliente", clienteUpdated);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @DeleteMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public ResponseEntity<?> delete(@PathVariable Integer id) {
      Map<String, Object> response = new HashMap<>();
      try {
         clienteService.delete(id);
      } catch (DataAccessException e) {
         response.put("Mensaje", "Error al eliminar el cliente en la base de datos.");
         response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      logService.insertDelete(clienteService.findById(id));
      response.put("Mensaje", "El cliente ha sido eliminado con éxito!");
      return new ResponseEntity<>(response, HttpStatus.OK);
   }

   @GetMapping("/logs")
   public List<Log> getAllLog() {
      return logService.getAll();
   }

   @DeleteMapping("/logs/{id}")
   public void deleteLog(@PathVariable Integer id) {
      logService.delete(id);
   }
}
