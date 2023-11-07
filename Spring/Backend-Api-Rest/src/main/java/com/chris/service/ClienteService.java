package com.chris.service;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;

import java.util.List;

public interface ClienteService {
   List<Cliente> getAll();

   Cliente findById(Integer id);

   Cliente insert(ClienteDao cliente);

   Cliente update(Integer id, ClienteDao cliente);

   void delete(Integer id);
}
