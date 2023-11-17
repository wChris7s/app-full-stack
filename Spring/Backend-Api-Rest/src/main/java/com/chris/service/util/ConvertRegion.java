package com.chris.service.util;

import com.chris.dao.RegionDao;
import com.chris.model.Region;

public class ConvertRegion {
   public static Region convertDaoToDto(RegionDao regionDAO) {
      return Region.builder()
       .id(regionDAO.getId())
       .nombre(regionDAO.getNombre())
       .build();
   }

   public static RegionDao convertDtoToDao(Region regionDTO) {
      return RegionDao.builder()
       .id(regionDTO.getId())
       .nombre(regionDTO.getNombre())
       .build();
   }
}
