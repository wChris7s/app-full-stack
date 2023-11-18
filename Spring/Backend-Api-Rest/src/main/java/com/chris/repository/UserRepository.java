package com.chris.repository;

import com.chris.dao.UserDao;
import com.chris.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
   UserDao findByUsername(String username);
}
