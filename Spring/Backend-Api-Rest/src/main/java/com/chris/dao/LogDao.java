package com.chris.dao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "logs_cliente")
public class LogDao {
   @Id
   @GeneratedValue(
    strategy = GenerationType.IDENTITY
   )
   private Integer id;
   @Column
   private String description;

   @Column(name = "operation_at")
   private String operationAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm:ss a"));

   public LogDao() {
   }

   public LogDao(String description) {
      this.description = description;
   }

   public LogDao(Integer id, String description, String operationAt) {
      this.id = id;
      this.description = description;
      this.operationAt = operationAt;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String operation) {
      this.description = operation;
   }

   public String getOperationAt() {
      return operationAt;
   }

   public void setOperationAt(String operationAt) {
      this.operationAt = operationAt;
   }
}
