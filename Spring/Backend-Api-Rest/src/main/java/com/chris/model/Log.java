package com.chris.model;

import java.util.Date;

public class Log {
   private Integer id;
   private String description;
   private String operationAt;

   public Log() {
   }

   public Log(String description) {
      this.description = description;
   }

   public Log(Integer id, String description, String operationAt) {
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

   public void setDescription(String description) {
      this.description = description;
   }

   public String getOperationAt() {
      return operationAt;
   }

   public void setOperationAt(String operationAt) {
      this.operationAt = operationAt;
   }
}
