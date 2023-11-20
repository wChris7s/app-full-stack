package com.chris.service;

import com.chris.entity.Cliente;
import com.chris.entity.Factura;
import com.chris.entity.Producto;
import com.chris.entity.Region;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClienteService {
   List<Cliente> getAll();

   List<Region> getAllRegion();

   Page<Cliente> getAll(Integer page, Integer size);

   Cliente findById(Integer id);

   Cliente insert(Cliente cliente);

   Cliente update(Integer id, Cliente cliente);

   Factura findFacturaById(Integer id);

   Factura insertFactura(Factura factura);

   void deleteFacturaById(Integer id);

   List<Producto> findProductoByNombre(String term);

   void delete(Integer id);
}
