package com.chris.service;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;
import com.chris.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {
   List<Cliente> getAll();

   List<Region> getAllRegion();

   Page<Cliente> getAll(Pageable pageable);

   Cliente findById(Integer id);

   Cliente insert(ClienteDao cliente);

   Cliente update(Integer id, ClienteDao cliente);

   void delete(Integer id);
}
