package com.chris.service;

import com.chris.dao.UserDao;
import com.chris.model.User_;
import com.chris.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
   private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

   @Autowired
   private UserRepository userRepository;

   @Override
   @Transactional(readOnly=true)
   public UserDao findByUsername(String username) {
      return userRepository.findByUsername(username);
   }

   @Override
   @Transactional(readOnly=true)
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserDao usuario = userRepository.findByUsername(username);

      if (usuario == null) {
         logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
         throw new UsernameNotFoundException("Error en el login: no existe el usuario '" + username + "' en el sistema!");
      }

      List<GrantedAuthority> authorities = usuario.getRoles()
       .stream()
       .map(role -> new SimpleGrantedAuthority(role.getNombre()))
       .peek(authority -> logger.info("Role: " + authority.getAuthority()))
       .collect(Collectors.toList());

      return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
   }
}
