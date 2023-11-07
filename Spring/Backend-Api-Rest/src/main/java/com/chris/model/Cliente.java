package com.chris.model;

import java.util.Date;

public class Cliente {
   private Integer id;
   private String nombre;
   private String apellido;
   private String email;
   private Date createAt;

   public Cliente() {
   }

   public Cliente(Integer id, String nombre, String apellido, String email, Date createAt) {
      this.id = id;
      this.nombre = nombre;
      this.apellido = apellido;
      this.email = email;
      this.createAt = createAt;
   }

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

   public String getApellido() {
      return apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Date getCreateAt() {
      return createAt;
   }

   public void setCreateAt(Date createAt) {
      this.createAt = createAt;
   }

   @Override
   public String toString() {
      return
       "Nombre: " + this.nombre  +
       ", Apellido: " + this.apellido +
       ", Email: " + this.email;
   }
}
