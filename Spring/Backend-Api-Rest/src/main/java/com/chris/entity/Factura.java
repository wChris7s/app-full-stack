package com.chris.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * Eager: Carga ansiosa => Carga todos los atributos de la relación.
 * Lazy: Carga Perezosa => Cada que se invoca el atributo getCliente recién se realiza la carga.
 * @PrePersist: Se ejecuta antes de persistir el objeto.
 */
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String descripcion;
   private String observacion;

   @Column(name = "create_at")
   @Temporal(TemporalType.DATE)
   private Date createAt;

   @JsonIgnoreProperties(value = {"facturas", "hibernateLazyInitializer", "handler"}, allowSetters = true)
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cliente_id")
   private Cliente cliente;

   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "factura_id")
   private List<ItemFactura> items;

   @PrePersist
   public void prePersist() {
      this.createAt = new Date();
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDescripcion() {
      return descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getObservacion() {
      return observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public Date getCreateAt() {
      return createAt;
   }

   public void setCreateAt(Date createAt) {
      this.createAt = createAt;
   }

   public Cliente getCliente() {
      return cliente;
   }

   public void setCliente(Cliente cliente) {
      this.cliente = cliente;
   }

   public List<ItemFactura> getItems() {
      return items;
   }

   public void setItems(List<ItemFactura> items) {
      this.items = items;
   }

   public Double getTotal() {
      Double total = 0.00;
      for (ItemFactura item : items) {
         total += item.getImporte();
      }
      return total;
   }
}

