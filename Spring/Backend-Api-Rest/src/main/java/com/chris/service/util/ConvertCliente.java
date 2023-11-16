package com.chris.service.util;

import com.chris.dao.ClienteDao;
import com.chris.model.Cliente;

public class ConvertCliente {
   public static Cliente convertDaoToDto(ClienteDao clienteDao) {
      return Cliente.builder()
       .id(clienteDao.getId())
       .nombre(clienteDao.getNombre())
       .apellido(clienteDao.getApellido())
       .email(clienteDao.getEmail())
       .createAt(clienteDao.getCreateAt())
       .foto(clienteDao.getFoto())
       .region(ConvertRegion.convertDaoToDto(clienteDao.getRegion()))
       .build();
   }

   public static ClienteDao convertDtoToDao(Cliente clienteDto) {
      return ClienteDao.builder()
       .id(clienteDto.getId())
       .nombre(clienteDto.getNombre())
       .apellido(clienteDto.getApellido())
       .email(clienteDto.getEmail())
       .createAt(clienteDto.getCreateAt())
       .foto(clienteDto.getFoto())
       .region(ConvertRegion.convertDtoToDao(clienteDto.getRegion()))
       .build();
   }
}
