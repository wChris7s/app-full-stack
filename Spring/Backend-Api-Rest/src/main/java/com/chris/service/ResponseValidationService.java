package com.chris.service;

import com.chris.entity.Cliente;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface ResponseValidationService {
   ResponseEntity<?> getById(Integer id);
   ResponseEntity<?> emptyFields(BindingResult result);
   ResponseEntity<?> bdError(String message, DataAccessException e);
   ResponseEntity<?> success(Cliente cliente, HttpStatus httpStatus, String message);
}
