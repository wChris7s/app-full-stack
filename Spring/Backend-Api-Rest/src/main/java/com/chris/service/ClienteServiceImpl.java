package com.chris.service;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;
import com.chris.repository.ClienteRepository;
import com.chris.service.util.ConvertCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

   @Autowired
   private ClienteRepository clienteRepository;

   @Override
   public List<Cliente> getAll() {
      return clienteRepository.findAll()
       .stream()
       .map((ConvertCliente::convertDaoToDto))
       .collect(Collectors.toList());
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
