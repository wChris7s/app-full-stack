package com.chris.service;

import com.chris.dao.ClienteDao;
import com.chris.dao.RegionDAO;
import com.chris.model.Cliente;
import com.chris.model.Region;
import com.chris.repository.ClienteRepository;
import com.chris.repository.RegionRepository;
import com.chris.service.util.ConvertCliente;
import com.chris.service.util.ConvertRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

   @Autowired
   private ClienteRepository clienteRepository;

   @Autowired
   private RegionRepository regionRepository;

   @Override
   public List<Cliente> getAll() {
      return clienteRepository.findAll()
       .stream()
       .map(ConvertCliente::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   public List<Region> getAllRegion() {
      return regionRepository.findAll()
       .stream()
       .map(ConvertRegion::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   public Page<Cliente> getAll(Pageable pageable) {
      return clienteRepository.findAll(pageable).map(ConvertCliente::convertDaoToDto);
   }

   @Override
   public Cliente findById(Integer id) {
      return clienteRepository.findById(id).map(ConvertCliente::convertDaoToDto).orElse(null);
   }

   @Override
   public Cliente insert(ClienteDao cliente) {
      ClienteDao c = clienteRepository.save(cliente);
      return ConvertCliente.convertDaoToDto(c);
   }

   @Override
   public Cliente update(Integer id, ClienteDao cliente) {
      Cliente c = findById(id);
      c.setNombre(cliente.getNombre());
      c.setApellido(cliente.getApellido());
      c.setEmail(cliente.getEmail());
      return c;
   }

   @Override
   public void delete(Integer id) {
      clienteRepository.deleteById(id);
   }
}
