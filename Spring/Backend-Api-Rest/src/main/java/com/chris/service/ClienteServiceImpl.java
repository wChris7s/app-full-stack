package com.chris.service;

import com.chris.entity.Cliente;
import com.chris.entity.Factura;
import com.chris.entity.Producto;
import com.chris.entity.Region;
import com.chris.repository.ClienteRepository;
import com.chris.repository.FacturaRepository;
import com.chris.repository.ProductoRepository;
import com.chris.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

   @Autowired
   private ClienteRepository clienteRepository;

   @Autowired
   private RegionRepository regionRepository;

   @Autowired
   private FacturaRepository facturaRepository;

   @Autowired
   private ProductoRepository productoRepository;

   @Override
   @Transactional(readOnly = true)
   public List<Cliente> getAll() {
      return clienteRepository.findAll();
   }

   @Override
   @Transactional(readOnly = true)
   public List<Region> getAllRegion() {
      return regionRepository.findAll();
   }

   @Override
   @Transactional(readOnly = true)
   public Page<Cliente> getAll(Integer page, Integer size) {
      Pageable pageable = PageRequest.of(page, size);
      return clienteRepository.findAll(pageable);
   }

   @Override
   @Transactional(readOnly = true)
   public Cliente findById(Integer id) {
      return clienteRepository.findById(id).orElse(null);
   }

   @Override
   @Transactional
   public Cliente insert(Cliente cliente) {
      return clienteRepository.save(cliente);
   }

   @Override
   @Transactional
   public Cliente update(Integer id, Cliente cliente) {
      Cliente c = findById(id);
      c.setNombre(cliente.getNombre());
      c.setApellido(cliente.getApellido());
      c.setEmail(cliente.getEmail());
      return c;
   }

   @Override
   @Transactional(readOnly = true)
   public Factura findFacturaById(Integer id) {
      return facturaRepository.findById(id).orElse(null);
   }

   @Override
   @Transactional
   public Factura insertFactura(Factura factura) {
      return facturaRepository.save(factura);
   }

   @Override
   @Transactional
   public void deleteFacturaById(Integer id) {
      facturaRepository.deleteById(id);
   }

   @Override
   @Transactional
   public List<Producto> findProductoByNombre(String term) {
      return productoRepository.findByNombreContainingIgnoreCase(term);
   }

   @Override
   @Transactional
   public void delete(Integer id) {
      clienteRepository.deleteById(id);
   }
}
