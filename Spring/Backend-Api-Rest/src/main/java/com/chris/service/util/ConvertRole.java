package com.chris.service.util;

import com.chris.dao.ClienteDao;
import com.chris.dao.RoleDao;
import com.chris.model.Cliente;
import com.chris.model.Role;

public class ConvertRole {
   public static Role convertDaoToDto(RoleDao roleDao) {
      return Role.builder()
       .id(roleDao.getId())
       .nombre(roleDao.getNombre())
       .build();
   }

   public static RoleDao convertDtoToDao(Role roleDTO) {
      return RoleDao.builder()
       .id(roleDTO.getId())
       .nombre(roleDTO.getNombre())
       .build();
   }
}
