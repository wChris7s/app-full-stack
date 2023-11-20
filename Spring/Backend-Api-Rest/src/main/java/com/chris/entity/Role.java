package com.chris.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")

public class Role implements Serializable {
   @Id
   @GeneratedValue(
    strategy = GenerationType.IDENTITY
   )
   private Integer id;
   @Column(unique = true, length = 20)
   private String nombre;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }
}