package com.chris.service;

import com.chris.dao.LogDao;
import com.chris.model.Cliente;
import com.chris.model.Log;
import com.chris.repository.LogRepository;
import com.chris.service.util.ConvertLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
   @Autowired
   LogRepository logRepository;

   @Override
   public List<Log> getAll() {
      return logRepository.findAll()
       .stream()
       .map(ConvertLog::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   public void insert(Cliente cliente) {
      LogDao logDao = new LogDao("Se inserto al cliente => " + cliente.toString());
      logRepository.save(logDao);
   }

   @Override
   public void insertGetAll(List<Cliente> clientes) {
      LogDao logDao = new LogDao("Se listo " +
       ((clientes.size() == 1) ? "1 cliente" : clientes.size() + " clientes")
      );
      logRepository.save(logDao);
   }

   @Override
   public void insertGetById(Cliente cliente) {
      LogDao logDao = new LogDao("Se busco al cliente de ID " + cliente.getId() + " => " + cliente);
      logRepository.save(logDao);
   }

   @Override
   public void insertUpdate(Cliente cliente1, Cliente cliente2) {
      LogDao logDao = new LogDao(
       "Se actualizo al cliente => " + cliente1.toString() + " a => " + cliente2.toString());
      logRepository.save(logDao);
   }

   @Override
   public void insertDelete(Cliente cliente) {
      LogDao logDao = new LogDao("Se elimino al cliente de ID " + cliente.getId() + " => " + cliente);
      logRepository.save(logDao);
   }

   @Override
   public void delete(Integer id) {
      logRepository.deleteById(id);
   }
}
