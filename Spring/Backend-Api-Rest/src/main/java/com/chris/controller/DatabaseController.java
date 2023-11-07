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

   private final AtomicInteger logId = new AtomicInteger(1);

   @GetMapping("/clientes")
   public List<Cliente> getAll() {
      List<Cliente> c = clienteService.getAll();
      LogDao logDao = new LogDao(logId.getAndIncrement(), "Se listo " + c.size() + " clientes.");
      logService.insert(logDao);
      return c;
   }

   @GetMapping("/clientes/{id}")
   public Cliente getById(@PathVariable Integer id) {
      Cliente c = clienteService.findById(id);
      LogDao logDao = new LogDao(logId.getAndIncrement(), "Se busco al cliente " + c.getNombre() + " con ID " + c.getId() + ".");
      logService.insert(logDao);
      return c;
   }

   @PostMapping("/clientes")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente insert(@RequestBody ClienteDao clienteDao) {
      Cliente c = clienteService.insert(clienteDao);
      LogDao logDao = new LogDao(logId.getAndIncrement(), "Se inserto al cliente " + c.getNombre() + " " + c.getApellido() + ".");
      logService.insert(logDao);
      return c;
   }

   @PutMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.CREATED)
   public Cliente update(@RequestBody ClienteDao clienteDao, @PathVariable Integer id) {
      Cliente c = clienteService.findById(id);

      LogDao logDao = new LogDao(logId.getAndIncrement(),
       "Se actualizo al cliente " + c.getNombre() + " " + c.getApellido()
        + " con email " + c.getEmail() + " a " + clienteDao.getNombre() + " " + clienteDao.getApellido()
        + " con email " + clienteDao.getEmail() + ".");

      logService.insert(logDao);

      c.setNombre(clienteDao.getNombre());
      c.setApellido(clienteDao.getApellido());
      c.setEmail(clienteDao.getEmail());
      return c;
   }

   @DeleteMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable Integer id) {
      LogDao logDao = new LogDao(logId.getAndIncrement(), "Se elimino al cliente con ID " + id + ".");
      logService.insert(logDao);

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
