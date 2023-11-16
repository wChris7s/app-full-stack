package com.chris.dao;

import com.chris.model.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
   private String foto;

   // Carga Perezosa => Cada que se invoca el atributo getRegíon recién se realiza la carga.
   // LAZY genera un proxy o puente hacía el objeto RegionDAO
   // El proxy genera atributos adicionales, pero estos se deben quitar del Json.
   @NotNull(message = "La región no puede ser vacía.")
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "region_id")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private RegionDAO region;
}
