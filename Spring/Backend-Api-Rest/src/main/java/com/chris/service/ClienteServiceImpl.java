package com.chris.service;

import com.chris.dao.ClienteDao;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

   @Autowired
   private ClienteRepository clienteRepository;

   @Autowired
   private RegionRepository regionRepository;

   @Override
   @Transactional(readOnly=true)
   public List<Cliente> getAll() {
      return clienteRepository.findAll()
       .stream()
       .map(ConvertCliente::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   @Transactional(readOnly=true)
   public List<Region> getAllRegion() {
      return regionRepository.findAll()
       .stream()
       .map(ConvertRegion::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   @Transactional(readOnly=true)
   public Page<Cliente> getAll(Pageable pageable) {
      return clienteRepository.findAll(pageable).map(ConvertCliente::convertDaoToDto);
   }

   @Override
   @Transactional(readOnly=true)
   public Cliente findById(Integer id) {
      return clienteRepository.findById(id).map(ConvertCliente::convertDaoToDto).orElse(null);
   }

   @Override
   @Transactional
   public Cliente insert(ClienteDao cliente) {
      ClienteDao c = clienteRepository.save(cliente);
      return ConvertCliente.convertDaoToDto(c);
   }

   @Override
   @Transactional
   public Cliente update(Integer id, ClienteDao cliente) {
      Cliente c = findById(id);
      c.setNombre(cliente.getNombre());
      c.setApellido(cliente.getApellido());
      c.setEmail(cliente.getEmail());
      return c;
   }

   @Override
   @Transactional
   public void delete(Integer id) {
      clienteRepository.deleteById(id);
   }
}
