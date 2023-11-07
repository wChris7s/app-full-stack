package com.chris.service.util;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;

public class ConvertCliente {
   public static Cliente convertDaoToDto(ClienteDao clienteDao) {
      return new Cliente(
       clienteDao.getId(), clienteDao.getNombre(), clienteDao.getApellido(), clienteDao.getEmail(), clienteDao.getCreateAt()
      );
   }

   public static ClienteDao convertDtoToDao(Cliente clienteDto) {
      return new ClienteDao(
       clienteDto.getId(), clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getEmail(), clienteDto.getCreateAt()
      );
   }
}
