package com.chris.service;

import com.chris.dao.UserDao;
import com.chris.model.User_;

public interface UserService {
   UserDao findByUsername(String username);
}
