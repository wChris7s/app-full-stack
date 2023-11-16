package com.chris.repository;

import com.chris.dao.ClienteDao;
import com.chris.dao.RegionDAO;
import com.chris.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDao, Integer> { }
