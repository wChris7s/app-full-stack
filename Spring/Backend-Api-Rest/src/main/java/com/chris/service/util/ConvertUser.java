package com.chris.service.util;

import com.chris.dao.UserDao;
import com.chris.model.User_;

import java.util.stream.Collectors;

public class ConvertUser {
   public static User_ convertDaoToDto(UserDao userDao) {
      return User_.builder()
       .id(userDao.getId())
       .username(userDao.getUsername())
       .password(userDao.getPassword())
       .enabled(userDao.getEnabled())
       .roles(userDao.getRoles()
        .stream().map(ConvertRole::convertDaoToDto).collect(Collectors.toList()))
       .build();
   }

   public static UserDao convertDtoToDao(User_ userDTO) {
      return UserDao.builder()
       .id(userDTO.getId())
       .username(userDTO.getUsername())
       .password(userDTO.getPassword())
       .enabled(userDTO.getEnabled())
       .roles(userDTO.getRoles()
        .stream().map(ConvertRole::convertDtoToDao).collect(Collectors.toList()))
       .build();
   }
}
