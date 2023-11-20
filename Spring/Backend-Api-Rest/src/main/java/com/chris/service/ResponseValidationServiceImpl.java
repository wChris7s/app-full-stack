package com.chris.service;

import com.chris.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResponseValidationServiceImpl implements ResponseValidationService {
   @Autowired
   private ClienteService clienteService;

   @Override
   public ResponseEntity<?> getById(Integer id) {
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

   @Override
   public ResponseEntity<?> emptyFields(BindingResult result) {
      Map<String, Object> response = new HashMap<>();
      List<String> errors = result.getFieldErrors()
       .stream()
       .map(fieldError -> "El campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
       .collect(Collectors.toList());
      response.put("errors", errors);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
   }

   @Override
   public ResponseEntity<?> bdError(String message, DataAccessException e) {
      Map<String, Object> response = new HashMap<>();
      response.put("mensaje", "Error al " + message + " en la base de datos.");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @Override
   public ResponseEntity<?> success(Cliente cliente, HttpStatus httpStatus, String message) {
      Map<String, Object> response = new HashMap<>();
      response.put("mensaje", "El cliente ha sido " + message + " con éxito.");
      response.put("cliente", cliente);
      return new ResponseEntity<>(response, httpStatus);
   }
}
