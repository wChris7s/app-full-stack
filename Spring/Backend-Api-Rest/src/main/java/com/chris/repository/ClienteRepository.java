package com.chris.repository;

import com.chris.dao.ClienteDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDao, Integer> {
}
