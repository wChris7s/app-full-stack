package com.chris.dao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "cliente")
public class ClienteDao {
   @Id
   @GeneratedValue(
    strategy = GenerationType.IDENTITY
   )
   private Integer id;

   @NotEmpty(message = "No puede estar vacío.")   // Validador
   @Size(min = 4, max = 12, message = "El tamaño del mensaje tiene que estar entre 4 y 12.") // Validador
   @Column(nullable = false)
   private String nombre;

   @NotEmpty(message = "No puede estar vacío.")   // Validador
   @Column  // Validador
   private String apellido;

   @NotEmpty(message = "No puede estar vacío.")   // Validador
   @Email(message = "El Email tiene un formato erróneo.")   // Validador
   @Column(nullable = false, unique = true)
   private String email;

   @NotNull(message = "No puede estar vacío.")   // Validador
   @Column(name = "create_at")
   @Temporal(TemporalType.DATE)
   private Date createAt;

   /*
   @PrePersist
   public void onInit() {
      createAt = new Date();
   }
   */

   public ClienteDao() {
   }

   public ClienteDao(Integer id, String nombre, String apellido, String email, Date createAt) {
      this.id = id;
      this.nombre = nombre;
      this.apellido = apellido;
      this.email = email;
      this.createAt = createAt;
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

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getId() {
      return id;
   }
}
