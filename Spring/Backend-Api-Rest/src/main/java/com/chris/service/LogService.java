package com.chris.service;

import com.chris.dao.LogDao;
import com.chris.model.Log;

import java.util.List;

public interface LogService {
   List<Log> getAll();

   void insert(LogDao logDao);

   void delete(Integer id);
}
