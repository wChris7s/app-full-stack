package com.chris.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class UserDao {
   @Id
   @GeneratedValue(
    strategy = GenerationType.IDENTITY
   )
   private Integer id;
   @Column(unique = true, length = 20)
   private String username;
   @Column(length = 60)
   private String password;
   private Boolean enabled;

   /* ================== */
   private String nombre;
   private String apellido;
   private String email;
   /* ================== */

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"),
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
   )
   private List<RoleDao> roles;
}
