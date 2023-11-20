package com.chris.service;

import com.chris.entity.Usuario;

public interface UserService {
   Usuario findByUsername(String username);
}
