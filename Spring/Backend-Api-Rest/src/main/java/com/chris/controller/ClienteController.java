package com.chris.controller;

import com.chris.entity.Cliente;
import com.chris.entity.Region;
import com.chris.service.ClienteService;
import com.chris.service.ResponseValidationService;
import com.chris.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {
   @Autowired
   private ClienteService clienteService;

   @Autowired
   private ResponseValidationService responseValidationService;

   @Autowired
   private UploadFileService uploadFileService;

   @GetMapping("/clientes")
   public List<Cliente> getAll() {
      return clienteService.getAll();
   }

   @GetMapping("/clientes/page/{page}")
   public Page<Cliente> getAll(@PathVariable Integer page) {
      return clienteService.getAll(page, 5);
   }

   @Secured({"ROLE_ADMIN", "ROLE_USER"})
   @GetMapping("/clientes/{id}")
   public ResponseEntity<?> getById(@PathVariable Integer id) {
      return responseValidationService.getById(id);
   }

   @Secured("ROLE_ADMIN")
   @PostMapping("/clientes")
   public ResponseEntity<?> insert(@Valid @RequestBody Cliente cliente, BindingResult result) {
      Cliente c;
      if (result.hasErrors()) {
         return responseValidationService.emptyFields(result);
      }
      try {
         c = clienteService.insert(cliente);
      } catch (DataAccessException e) {
         return responseValidationService.bdError("insertar", e);
      }
      return responseValidationService.success(c, HttpStatus.CREATED, "insertado");
   }

   @Secured("ROLE_ADMIN")
   @PutMapping("/clientes/{id}")
   public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Integer id) {
      Cliente c = clienteService.findById(id);
      Cliente clienteUpdated;
      if (result.hasErrors()) {
         return responseValidationService.emptyFields(result);
      }
      try {
         c.setNombre(cliente.getNombre());
         c.setApellido(cliente.getApellido());
         c.setEmail(cliente.getEmail());
         c.setCreateAt(cliente.getCreateAt());
         c.setRegion(cliente.getRegion());
         clienteUpdated = clienteService.insert(cliente);
      } catch (DataAccessException e) {
         return responseValidationService.bdError("actualizar", e);
      }
      return responseValidationService.success(clienteUpdated, HttpStatus.CREATED, "actualizado");
   }

   @Secured("ROLE_ADMIN")
   @DeleteMapping("/clientes/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public ResponseEntity<?> delete(@PathVariable Integer id) {
      Map<String, Object> response = new HashMap<>();
      try {
         Cliente c = clienteService.findById(id);
         String nombreFotoAnterior = c.getFoto();
         uploadFileService.delete(nombreFotoAnterior);
         clienteService.delete(id);
      } catch (DataAccessException e) {
         return responseValidationService.bdError("eliminar", e);
      }
      response.put("mensaje", "El cliente ha sido eliminado con éxito.");
      return new ResponseEntity<>(response, HttpStatus.OK);
   }

   @Secured({"ROLE_ADMIN", "ROLE_USER"})
   @PostMapping("/clientes/upload")
   public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Integer id) {
      Map<String, Object> response = new HashMap<>();
      Cliente c = clienteService.findById(id);
      if (!archivo.isEmpty()) {
         String fileName;
         try {
            fileName = uploadFileService.copy(archivo);
         } catch (IOException e) {
            response.put("mensaje", "Error al subir la imagen.");
            response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         }

         String nombreFotoAnterior = c.getFoto();
         uploadFileService.delete(nombreFotoAnterior);

         c.setFoto(fileName);
         clienteService.insert(c);
         response.put("cliente", c);
         response.put("mensaje", "La imagen se subió correctamente.");
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

   @GetMapping("/upload/img/{nombreFoto:.+}")
   public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
      Resource resource;
      try {
         resource = uploadFileService.loadImage(nombreFoto);
      } catch (MalformedURLException e) {
         throw new RuntimeException(e);
      }
      HttpHeaders cabecera = new HttpHeaders();
      cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

      return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
   }

   @Secured("ROLE_ADMIN")
   @GetMapping("/clientes/regiones")
   public List<Region> findAllRegion() {
      return clienteService.getAllRegion();
   }
}
