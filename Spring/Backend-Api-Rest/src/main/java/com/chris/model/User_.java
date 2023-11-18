package com.chris.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class User_ {
   private Integer id;
   private String username;
   private String password;
   private Boolean enabled;
   private List<Role> roles;
}
