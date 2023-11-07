package com.chris.service.util;

import com.chris.dao.LogDao;
import com.chris.model.Log;

public class ConvertLog {
   public static Log convertDaoToDto(LogDao logDao) {
      return new Log(logDao.getId(), logDao.getDescription(), logDao.getOperationAt());
   }

   public static LogDao convertDtoToDao(Log log) {
      return new LogDao(log.getId(), log.getDescription(), log.getOperationAt());
   }
}
