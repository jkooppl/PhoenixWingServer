package com.pizza73.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * UploadReport
 * 
 * @author c
 *
 * 21-Jun-2006
 * Copyright 2005 ctrlSpace (1150894 Alberta Ltd.)
 */
public class UploadReport {
   private int row;
   private int cell;
   private String message;
   
   /**
    * 
    */
   public UploadReport() {
      super();
   }

   public UploadReport(int row, int cell, String message) {
      super();
      this.row = row;
      this.cell = cell;
      this.message = message;
   }
   
   public int getCell() {
      return this.cell;
   }

   public void setCell(int cell) {
      this.cell = cell;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public int getRow() {
      return this.row;
   }

   public void setRow(int row) {
      this.row = row;
   }

   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(
         this, ToStringStyle.MULTI_LINE_STYLE);
   }

   
}
