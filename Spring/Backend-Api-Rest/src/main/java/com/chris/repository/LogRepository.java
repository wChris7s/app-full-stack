package com.chris.repository;

import com.chris.dao.LogDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogDao, Integer> {
}
