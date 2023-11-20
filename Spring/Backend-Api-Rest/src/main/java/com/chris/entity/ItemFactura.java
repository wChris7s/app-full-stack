package com.chris.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

   @Id
   @GeneratedValue(
    strategy = GenerationType.IDENTITY
   )

   @Getter
   private Integer id;

   @Getter
   private Integer cantidad;

   @Getter
   @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "producto_id")
   private Producto producto;


   public void setId(Integer id) {
      this.id = id;
   }

   public void setCantidad(Integer cantidad) {
      this.cantidad = cantidad;
   }

   public void setProducto(Producto producto) {
      this.producto = producto;
   }

   public Double getImporte() {
      return cantidad.doubleValue() * producto.getPrecio();
   }
}
