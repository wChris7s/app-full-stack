package com.chris.service.util;

import com.chris.dao.ClienteDao;
import com.chris.dao.RegionDAO;
import com.chris.model.Cliente;
import com.chris.model.Region;

public class ConvertRegion {
   public static Region convertDaoToDto(RegionDAO regionDAO) {
      return Region.builder()
       .id(regionDAO.getId())
       .nombre(regionDAO.getNombre())
       .build();
   }

   public static RegionDAO convertDtoToDao(Region regionDTO) {
      return RegionDAO.builder()
       .id(regionDTO.getId())
       .nombre(regionDTO.getNombre())
       .build();
   }
}
