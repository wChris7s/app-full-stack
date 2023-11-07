package com.chris.service;

import com.chris.dao.LogDao;
import com.chris.model.Log;
import com.chris.repository.LogRepository;
import com.chris.service.util.ConvertLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
   @Autowired
   LogRepository logRepository;

   @Override
   public List<Log> getAll() {
      return logRepository.findAll()
       .stream()
       .map(ConvertLog::convertDaoToDto)
       .collect(Collectors.toList());
   }

   @Override
   public void insert(LogDao logDao) {
      logRepository.save(logDao);
   }

   @Override
   public void delete(Integer id) {
      logRepository.deleteById(id);
   }
}
