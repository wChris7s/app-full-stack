package com.chris.dao;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regiones")

public class RegionDAO {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private String nombre;
}
