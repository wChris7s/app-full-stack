package com.chris.repository;

import com.chris.dao.RegionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<RegionDAO, Integer> {
}
