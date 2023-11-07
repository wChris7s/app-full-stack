package com.chris.controller;

import com.chris.dao.ClienteDao;
import com.chris.dao.LogDao;
import com.chris.model.Cliente;
import com.chris.model.Log;
import com.chris.service.ClienteService;
import com.chris.service.LogService;
import com.chris.service.util.ConvertCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
   public Cliente getById(@PathVariable Integer id) {
      Cliente cliente = clienteService.findById(id);
      logService.insertGetById(cliente);
      return cliente;
   }

   @PostMapping("/clientes")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente insert(@RequestBody ClienteDao clienteDao) {
      Cliente cliente = clienteService.insert(clienteDao);
      logService.insert(cliente);
      return cliente;
   }

   @PutMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente update(@RequestBody ClienteDao clienteDao, @PathVariable Integer id) {
      Cliente cliente = clienteService.findById(id);
      logService.insertUpdate(cliente, ConvertCliente.convertDaoToDto(clienteDao));
      cliente.setNombre(clienteDao.getNombre());
      cliente.setApellido(clienteDao.getApellido());
      cliente.setEmail(clienteDao.getEmail());
      return cliente;
   }

   @DeleteMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable Integer id) {
      logService.insertDelete(clienteService.findById(id));
      clienteService.delete(id);
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
