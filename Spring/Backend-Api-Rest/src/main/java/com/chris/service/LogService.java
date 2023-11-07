package com.chris.service;

import com.chris.dao.LogDao;
import com.chris.model.Cliente;
import com.chris.model.Log;

import java.util.List;

public interface LogService {
   List<Log> getAll();

   void insert(Cliente cliente);

   void insertGetAll(List<Cliente> clientes);

   void insertGetById(Cliente cliente);

   void insertUpdate(Cliente cliente1, Cliente cliente2);

   void insertDelete(Cliente cliente);

   void delete(Integer id);
}
