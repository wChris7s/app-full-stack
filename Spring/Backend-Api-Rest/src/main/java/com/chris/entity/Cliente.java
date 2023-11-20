package com.chris.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
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
   private String apellido;

   @NotEmpty(message = "No puede estar vacío.")   // Validador
   @Email(message = "El Email tiene un formato erróneo.")   // Validador
   @Column(nullable = false, unique = true)
   private String email;

   @NotNull(message = "No puede estar vacío.")   // Validador
   @Column(name = "create_at")
   @Temporal(TemporalType.DATE)
   private Date createAt;
   private String foto;

   // Carga Perezosa => Cada que se invoca el atributo getRegíon recién se realiza la carga.
   // LAZY genera un proxy o puente hacía el objeto RegionDAO
   // El proxy genera atributos adicionales, pero estos se deben quitar del Json.
   @NotNull(message = "La región no puede ser vacía.")
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "region_id")
   @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
   private Region region;

   // CascadeType.ALL: Si se elimina un cliente, se eliminan todas sus facturas.
   // allowSetters=true: Permite que se pueda modificar el cliente desde la factura.
   @JsonIgnoreProperties(value={"cliente", "hibernateLazyInitializer", "handler"}, allowSetters=true)
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
   private List<Factura> facturas;

   public Cliente() {
      this.facturas = new ArrayList<>();
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

   public String getFoto() {
      return foto;
   }

   public void setFoto(String foto) {
      this.foto = foto;
   }

   public Region getRegion() {
      return region;
   }

   public void setRegion(Region region) {
      this.region = region;
   }

   public List<Factura> getFacturas() {
      return facturas;
   }

   public void setFacturas(List<Factura> facturas) {
      this.facturas = facturas;
   }
}
