package com.chris.model;

import com.chris.dao.RegionDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Cliente {
   private Integer id;
   private String nombre;
   private String apellido;
   private String email;
   private Date createAt;
   private String foto;
   private Region region;

   @Override
   public String toString() {
      return
       "Nombre: " + this.nombre +
        ", Apellido: " + this.apellido +
        ", Email: " + this.email;
   }
}
